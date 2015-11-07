from .models import ContentCiphered
import hashlib
from .aescipher import AESCipher, Random
from pbkdf2 import PBKDF2
from django.core.files.storage import default_storage
from iedcs.settings.base import BASE_DIR
import rsa
from Crypto.Cipher import AES
import base64

# max iterations for the file derivation process
max_iterations = 10


def encrypt_book_content(book, user, device):
    book_content = default_storage.open(book.get_file_path()).read()

    # get or set the random 2
    if not exists_database_content_by_user_and_book("random2", user, book):
        random2 = Random.new().read(16)
        random2 = hashlib.sha224(random2).hexdigest()
        store_database_content_by_user_and_book("random2", random2, user, book)
    else:
        random2 = get_database_content_by_user_and_book("random2", user, book)

    # get or set the random 1
    if not exists_database_content_by_user_and_book("random1", user, book):
        random1 = Random.new().read(16)
        random1 = hashlib.sha224(random1).hexdigest()
        store_database_content_by_user_and_book("random1", random1, user, book)
    else:
        random1 = get_database_content_by_user_and_book("random1", user, book)

    # calculating the n for the iterations
    number = reduce(lambda x, y: x + y, [int(c) for c in random2 if c.isdigit()], 0)
    n = divmod(number, max_iterations)[1]

    iv_array = bytearray()

    # calculating the file key
    # generate rd1
    rd1 = rd1_process(random2, user, book, random2)
    # for get the iv
    iv_array += base64.b64decode(rd1)[:AES.block_size]

    # generate rd2
    rd2 = rd2_process(rd1, user, book, random1)

    # generate rd3
    rd3 = rd3_process(rd2, n, user, book, random2)
    iv_array += base64.b64decode(rd3)[:AES.block_size]

    file_key = rd3
    key = PBKDF2(file_key, random2).read(32)

    # get or set the book signed
    private_key_player = default_storage.open(BASE_DIR + '/media/player/v00/private.key').read()
    priv_key = rsa.PrivateKey.load_pkcs1(private_key_player)
    book_signed = base64.b64encode(rsa.sign(default_storage.open(book.get_file_path()), priv_key, 'SHA-256'))

    # cipher with AES
    k1 = PBKDF2(user.username + "jnpc" + book.identifier, book.identifier).read(32)
    random2 = AESCipher.encrypt(random2, k1)

    class BookSecurityResult:
        def __init__(self, rdn2, bs, bc):
            self.random2 = rdn2
            self.book_signed = bs
            self.book_ciphered = bc

    # append iv array
    rd2 = str(iv_array) + base64.b64decode(random2)
    random2 = base64.b64encode(rd2)

    if not exists_database_content_by_user_and_book("book_content_ciphered_iv", user, book):
        book_content_ciphered = AESCipher.encrypt(content=book_content, key=key)
        book_content_ciphered_iv = base64.b64decode(book_content_ciphered)[:AES.block_size]
        book_content_ciphered_iv = base64.b64encode(book_content_ciphered_iv)
        store_database_content_by_user_and_book("book_content_ciphered_iv", book_content_ciphered_iv, user, book)
    else:
        book_content_ciphered_iv = base64.b64decode(get_database_content_by_user_and_book("book_content_ciphered_iv", user, book))
        book_content_ciphered = AESCipher.encrypt(book_content, key, book_content_ciphered_iv)

    return BookSecurityResult(rdn2=random2, bs=book_signed, bc=book_content_ciphered)


def rd1_process(rd, user, book, random2):
    # k1 = PBKDF2(sha224(user.username + "fcp" + book.identifier), random2)
    k1 = PBKDF2(hashlib.sha224(user.username + "fcp" + book.identifier).hexdigest(), random2).read(32)

    if not exists_database_content_by_user_and_book("rd1_iv", user, book):
        # rd1 = AES/CBC(rd, k1)
        rd1 = AESCipher.encrypt(rd, k1)
        rd1_iv = base64.b64decode(rd1)[:AES.block_size]
        rd1_iv = base64.b64encode(rd1_iv)
        store_database_content_by_user_and_book("rd1_iv", rd1_iv, user, book)
    else:
        rd1_iv = base64.b64decode(get_database_content_by_user_and_book("rd1_iv", user, book))
        # rd1 = AES/CBC(rd, k1)
        rd1 = AESCipher.encrypt(rd, k1, rd1_iv)

    # print "rd1_process: rd1:" + rd1

    return rd1


def rd2_process(rd1, user, book, random1):
    # k2 = PBKDF2(sha224(user.username + "deti" + random1), random1)
    k2 = PBKDF2(hashlib.sha224(user.username + "deti" + random1).hexdigest(), random1).read(32)

    # rd2 = AES/CBC(rd1, k2)
    if not exists_database_content_by_user_and_book("rd2_iv", user, book):
        rd2 = AESCipher.encrypt(rd1, k2)
        rd2_iv = base64.b64decode(rd2)[:AES.block_size]
        rd2_iv = base64.b64encode(rd2_iv)
        store_database_content_by_user_and_book("rd2_iv", rd2_iv, user, book)
    else:
        rd2_iv = base64.b64decode(get_database_content_by_user_and_book("rd2_iv", user, book))
        rd2 = AESCipher.encrypt(rd1, k2, rd2_iv)

    return rd2


def rd3_process(rd2, n, user, book, random2):
    # k3 = PBKDF2(hashlib.sha224(n + "ua" + book.identifier), random2)
    k3 = PBKDF2(hashlib.sha224(str(n) + "ua" + book.identifier).hexdigest(), random2).read(32)
    # print "rd3_process: rd2: " + rd2
    # print "rd3_process: k3:" + base64.b64encode(k3)
    # print "rd3_process: sha:" + hashlib.sha224(str(n) + "ua" + book.identifier).hexdigest()

    # rd3 = AES/CBC(rd2, k3)
    if not exists_database_content_by_user_and_book("rd3_iv", user, book):
        rd3 = AESCipher.encrypt(rd2, k3)
        rd3_iv = base64.b64decode(rd3)[:AES.block_size]
        rd3_iv = base64.b64encode(rd3_iv)
        store_database_content_by_user_and_book("rd3_iv", rd3_iv, user, book)
    else:
        rd3_iv = base64.b64decode(get_database_content_by_user_and_book("rd3_iv", user, book))
        # print "rd3_process: rd3_iv:" + base64.b64encode(rd3_iv)
        rd3 = AESCipher.encrypt(rd2, k3, rd3_iv)

    # print "rd3_process: rd3:" + rd3
    return rd3


def exists_database_content_by_user_and_book(alias, user, book):
    identifier = hashlib.sha224(alias+user.username+book.identifier).hexdigest()
    return ContentCiphered.objects.filter(identifier=identifier).count()


def get_database_content_by_user_and_book(alias, user, book):
    identifier = hashlib.sha224(alias+user.username+book.identifier).hexdigest()
    c = hashlib.sha224(user.username+user.email+user.last_name).hexdigest()

    if ContentCiphered.objects.filter(identifier=identifier).count() == 1:
        content_ciphered = ContentCiphered.objects.get(identifier=identifier)
        key = PBKDF2(c, identifier).read(32)
        content = AESCipher.decrypt(content_ciphered=content_ciphered.content, key=key)
        return content

    return False


def get_database_content_by_user(alias, user):
    identifier = hashlib.sha224(alias+user.username).hexdigest()
    c = hashlib.sha224(user.username+user.email+user.last_name).hexdigest()

    if ContentCiphered.objects.filter(identifier=identifier).count() == 1:
        content_ciphered = ContentCiphered.objects.get(identifier=identifier)
        key = PBKDF2(c, identifier).read(32)
        content = AESCipher.decrypt(content_ciphered=content_ciphered.content, key=key)
        return content

    return False


def store_database_content_by_user_and_book(alias, content, user, book):
    identifier = hashlib.sha224(alias+user.username+book.identifier).hexdigest()
    c = hashlib.sha224(user.username+user.email+user.last_name).hexdigest()
    key = PBKDF2(c, identifier).read(32)
    content = AESCipher.encrypt(content=content, key=key)
    ContentCiphered.objects.create(identifier=identifier, content=content)


def store_database_content_by_user(alias, content, user):
    identifier = hashlib.sha224(alias+user.username).hexdigest()
    c = hashlib.sha224(user.username+user.email+user.last_name).hexdigest()
    key = PBKDF2(c, identifier).read(32)
    content = AESCipher.encrypt(content=content, key=key)
    ContentCiphered.objects.create(identifier=identifier, content=content)

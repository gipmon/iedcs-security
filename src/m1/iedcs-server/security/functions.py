import tempfile
from .models import ContentCiphered
import hashlib
from .aescipher import AESCipher, Random
from pbkdf2 import PBKDF2
from django.core.files.storage import default_storage
from iedcs.settings import BASE_DIR
import rsa
from rsa.bigfile import *

# max iterations for the file derivation process
max_iterations = 10


def encrypt_book_content(book, user, device):
    book_content = book.original_file.read().decode('utf-8')

    # get or set the random 2
    exists = exists_database_content_by_user_and_book("random2", user, book)

    if not exists:
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

    # calculating the n for the iterationss = "ABCD"
    number = reduce(lambda x, y: x + y, [int(c) for c in random2 if c.isdigit()], 0)
    n = divmod(number, max_iterations)

    rd3 = random2
    # calculating the file key
    for i in range(0, n[1]):
        # k1 = PBKDF2(sha224(user.username + "fcp" + book.identifier), random2)
        k1 = PBKDF2(hashlib.sha224(user.username + "fcp" + book.identifier).hexdigest(), random2).read(32)

        # rd1 = AES/CBC(rd, k1)
        rd1 = AESCipher.encrypt(rd3, k1)

        # k2 = PBKDF2(sha224(user.username + "deti" + random1), random1)
        k2 = PBKDF2(hashlib.sha224(user.username + "deti" + random1).hexdigest(), random1).read(32)

        # rd2 = AES/CBC(rd1, k2)
        rd2 = AESCipher.encrypt(rd1, k2)

        # k3 = PBKDF2(hashlib.sha224(n + "ua" + book.identifier), random2)
        k3 = PBKDF2(hashlib.sha224(str(n) + "ua" + book.identifier).hexdigest(), random2).read(32)

        # rd3 = AES/CBC(rd2, k3)
        rd3 = AESCipher.encrypt(rd2, k3)

    file_key = rd3

    # cipher with player key private RSA
    private_key_player = default_storage.open(BASE_DIR + '/media/player/v00/private.key').read()
    public_key_device = device.public_key.read()
    crypt_tmp_1 = tempfile.TemporaryFile()

    with book.original_file.file as input_file:
        pubkey = rsa.PublicKey.load_pkcs1(public_key_device)
        encrypt_bigfile(input_file, crypt_tmp_1, pubkey)

    print "ok"
    # cipher with device key public RSA

    # cipher with File Key AES/CBC

    print file_key

    return book_content


def exists_database_content_by_user_and_book(alias, user, book):
    identifier = hashlib.sha224(alias+user.username+book.identifier).hexdigest()
    return ContentCiphered.objects.filter(identifier=identifier).count()


def get_database_content_by_user_and_book(alias, user, book):
    identifier = hashlib.sha224(alias+user.username+book.identifier).hexdigest()
    c = hashlib.sha224(user.username+user.email+user.last_name).hexdigest()

    if ContentCiphered.objects.filter(identifier=identifier).count() == 1:
        content_ciphered = ContentCiphered.objects.first()
        key = PBKDF2(c, identifier).read(32)
        content = AESCipher.decrypt(content_ciphered=content_ciphered.content, key=key)
        return content

    return False


def get_database_content_by_user(alias, user):
    identifier = hashlib.sha224(alias+user.username).hexdigest()
    c = hashlib.sha224(user.username+user.email+user.last_name).hexdigest()

    if ContentCiphered.objects.filter(identifier=identifier).count() == 1:
        content_ciphered = ContentCiphered.objects.first()
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

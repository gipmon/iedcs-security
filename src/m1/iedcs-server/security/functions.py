from .models import ContentCiphered
import hashlib
import struct
from .aescipher import AESCipher, Random

# max iterations for the file derivation process
max_iterations = 10


def encrypt_book_content(book, user):
    book_content = book.original_file.read().decode('utf-8')

    # get or set the random 2
    random2 = get_database_content_by_user_and_book("random2", user, book)

    if random2 == False:
        random2 = Random.new().read(16)
        random2 = hashlib.sha224(random2).hexdigest()
        store_database_content_by_user_and_book("random2", random2, user, book)

    # get or set the random 1
    random1 = get_database_content_by_user_and_book("random1", user, book)

    if random1 == False:
        random1 = Random.new().read(16)
        random1 = hashlib.sha224(random2).hexdigest()
        store_database_content_by_user_and_book("random1", random1, user, book)

    # calculating the n for the iterations
    n = struct.unpack("<L", random2)[0]
    n = divmod(n, max_iterations)

    # calculating the file key
    # rsa with player key private


    # rsa with device key public

    # pbkdf2 ...

    return book_content


def get_database_content_by_user_and_book(alias, user, book):
    identifier = hashlib.sha224(alias+user.username+book.identifier)
    key = hashlib.sha224(user.username+user.email+user.last_name)

    if ContentCiphered.objects.filter(identifier=identifier).count() == 1:
        content_ciphered = ContentCiphered.objects.first()
        content = AESCipher.decrypt(content_ciphered=content_ciphered.content, key=key)
        return content

    return False


def get_database_content_by_user(alias, user):
    identifier = hashlib.sha224(alias+user.username)
    key = hashlib.sha224(user.username+user.email+user.last_name)

    if ContentCiphered.objects.filter(identifier=identifier).count() == 1:
        content_ciphered = ContentCiphered.objects.first()
        content = AESCipher.decrypt(content_ciphered=content_ciphered.content, key=key)
        return content

    return False


def store_database_content_by_user_and_book(alias, content, user, book):
    identifier = hashlib.sha224(alias+user.username+book.identifier)
    key = hashlib.sha224(user.username+user.email+user.last_name)
    content = AESCipher.encrypt(content=content, key=key)
    ContentCiphered.objects.create(identifier=identifier, content=content)


def store_database_content_by_user(alias, content, user):
    identifier = hashlib.sha224(alias+user.username)
    key = hashlib.sha224(user.username+user.email+user.last_name)
    content = AESCipher.encrypt(content=content, key=key)
    ContentCiphered.objects.create(identifier=identifier, content=content)

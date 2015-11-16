from django.test import TestCase
from aescipher import AESCipher


class SecurityTestCase(TestCase):
    def setUp(self):
        pass

    def test_books(self):
        # AES encrypt and decrypt
        key = "asgadgdsgdsgsdgsdvsdvwelkdsgkjjs"
        c1 = AESCipher.encrypt("teste", key)
        self.assertEqual(AESCipher.decrypt(c1, key), "teste")

from django.test import TestCase
from .models import Restriction, BookRestrictions
from books.models import Book
from django.core.files.storage import default_storage
from iedcs.settings import BASE_DIR
from .restrictions import test_restriction
from authentication.models import Account, UserCollectedData
from collections import OrderedDict


class RestrictionsTestCase(TestCase):
    def setUp(self):
        self.ucd1 = UserCollectedData.objects.create(cpu_model="MacBook Pro", op_system="MacOS", ip="193.2.4.1",
                                                     country="PT")
        self.a1 = Account.objects.create(email="rl@gmail.com", username="rlopescunha", first_name="Rodrigo",
                                         last_name="Cunha", user_data=self.ucd1)
        self.b1 = Book.objects.create(name="Seguranca em Redes Informaticas", author="Andre Zuquete",
                                      production_date="2013-05-01",
                                      original_file=default_storage.path(BASE_DIR + '/media/books/pg6598.txt'))

    def test_restrictions(self):
        # testado booleanValue value
        r1 = Restriction.objects.create(aliasKey="country", restrictionFunction="restriction_country")
        r2 = Restriction.objects.create(aliasKey="cpu_model", restrictionFunction="restriction_cpu_model")
        rb1 = BookRestrictions.objects.create(book=self.b1, restriction = r1)
        rb2 = BookRestrictions.objects.create(book=self.b1, restriction = r2)

        #testar restriction
        self.assertEqual(test_restriction(r1.restrictionFunction, rb1.book, self.ucd1), False)
        self.assertEqual(test_restriction(r2.restrictionFunction, rb2.book, self.ucd1), True)

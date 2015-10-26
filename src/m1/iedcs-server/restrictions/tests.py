from django.test import TestCase
from .models import Restriction, BookRestrictions
from books.models import Book
from django.core.files.storage import default_storage
from iedcs.settings import BASE_DIR
from .restrictions import test_restriction
from collections import OrderedDict


class RestrictionsTestCase(TestCase):
    def setUp(self):
        self.b1 = Book.objects.create(name="Seguranca em Redes Informaticas", author="Andre Zuquete",
                                      production_date="2013-05-01",
                                      original_file=default_storage.path(BASE_DIR + '/media/books/pg6598.txt'))

    def test_restrictions(self):
        # testado booleanValue value
        r = Restriction.objects.create(aliasKey="production_date", restrictionFunction="restriction_production_date")
        rb = BookRestrictions.objects.create(book=self.b1, restriction = r)

        #testar restriction
        test_restriction(r.restrictionFunction, rb.book, "qualquer")



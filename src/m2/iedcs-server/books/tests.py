from django.test import TestCase
from .models import Book, OrderBook
from django.core.files.storage import default_storage
from iedcs.settings.base import BASE_DIR
from rest_framework.test import APIClient
from collections import OrderedDict
from authentication.models import Account


class BooksTestCase(TestCase):
    def setUp(self):
        self.b1 = Book.objects.create(name="Seguranca em Redes Informaticas", author="Andre Zuquete",
                                      production_date="2013-05-01",
                                      original_file=default_storage.path(BASE_DIR + '/media/books/pg6598.txt'))

        self.b2 = Book.objects.create(name="Cyclopedia of Telephony & Telegraphy Vol. 2",
                                      author="Kempster Miller et. al.",
                                      production_date="2010-08-15",
                                      original_file=default_storage.path(BASE_DIR + '/media/books/pg33437.txt'))

        self.a1 = Account.objects.create(email='test@test.com', username='test', first_name='unit', last_name='test', user_data=self.ucd1)

    def test_books(self):
        client = APIClient()

        url = "/api/v1/books/"
        response = client.get(url)
        res = response.data
        del res[0]["identifier"]
        del res[1]["identifier"]
        self.assertEqual(res, [OrderedDict([('name', u'Seguranca em Redes Informaticas'), ('production_date', '2013-05-01'), ('author', u'Andre Zuquete')]), OrderedDict([('name', u'Cyclopedia of Telephony & Telegraphy Vol. 2'), ('production_date', '2010-08-15'), ('author', u'Kempster Miller et. al.')])])

        client.force_authenticate(user=self.a1)

        url = "/api/v1/user_books/"
        data = {'book_identifier': self.b1.identifier}
        response = client.post(path=url, data=data)
        self.assertEqual(response.status_code, 201)

        url = "/api/v1/user_books/"
        data = {'book_identifier': self.b2.identifier}
        response = client.post(path=url, data=data)
        self.assertEqual(response.status_code, 201)

        self.assertEqual(OrderBook.objects.count(), 2)

        url = "/api/v1/user_books/"
        response = client.get(path=url)
        self.assertEqual(response.status_code, 200)
        self.assertEqual(len(response.data), 2)

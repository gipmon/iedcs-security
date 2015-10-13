from django.test import TestCase
from .models import Book
from rest_framework.test import APIClient


class FilesTestCase(TestCase):
    def setUp(self):
        Book.objects.create(name="Seguranca em Redes Informaticas", author="Andre Zuquete",
                            production_date="2013-05-01")

    def test_files(self):
        client = APIClient()

        url = "/api/v1/files/"
        response = client.get(url)
        # print response

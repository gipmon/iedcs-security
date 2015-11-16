from django.db import models
from authentication.models import Account
from books.models import Book


class File(models.Model):
    book = models.ForeignKey(Book, blank=False)
    buyer = models.ForeignKey(Account, blank=False)

    class Meta:
        unique_together = ('buyer', 'book',)

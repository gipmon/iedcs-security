from django.db import models

from authentication.models import Account
from books.models import Book
import uuid


class Order(models.Model):
    oder_identifier = models.CharField(max_length=128, default=uuid.uuid4, blank=False, unique=True)
    buyer = models.ForeignKey(Account, blank=False)


class OrderBook(models.Model):
    order = models.ForeignKey(Order, blank=False)
    book = models.ForeignKey(Book, blank=False)

    class Meta:
        unique_together = ('order', 'book',)

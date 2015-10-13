from django.db import models
import uuid

from authentication.models import Account
from books.models import Book


class File(models.Model):
    identifier = models.CharField(max_length=128, default=uuid.uuid4, blank=False, unique=True)
    path = models.FileField()
    book = models.ForeignKey(Book, blank=False)
    buyer = models.ForeignKey(Account, blank=False)

    class Meta:
        unique_together = ('buyer', 'book',)

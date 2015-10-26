from django.db import models
from django.core.validators import MinLengthValidator
import uuid
from books.models import Book


class Restriction(models.Model):
    aliasKey = models.CharField(max_length=128, blank=False, unique=True)
    restrictionFunction = models.CharField(max_length=128, blank = False)



class BookRestrictions(models.Model):
    book = models.ForeignKey(Book, blank=False)
    restriction = models.ForeignKey(Restriction, blank=False)

    class Meta:
        unique_together = ('book', 'restriction')

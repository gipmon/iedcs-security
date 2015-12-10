from django.db import models
from django.core.validators import MinLengthValidator
import uuid
from authentication.models import Account
from django.core.files.storage import default_storage
from iedcs.settings.base import BASE_DIR


class Book(models.Model):
    identifier = models.CharField(max_length=128, default=uuid.uuid4, blank=False, unique=True)

    name = models.CharField(max_length=128, blank=False, validators=[MinLengthValidator(1)])
    production_date = models.DateField()
    author = models.CharField(max_length=128, blank=False, validators=[MinLengthValidator(1)])

    original_file = models.TextField()

    def get_file_path(self):
        return default_storage.path(BASE_DIR+'/media/books/'+self.original_file)

    class Meta:
        unique_together = ('name', 'production_date', 'author',)


class OrderBook(models.Model):
    buyer = models.ForeignKey(Account, blank=False)
    book = models.ForeignKey(Book, blank=False)

    class Meta:
        unique_together = ('book', 'buyer',)

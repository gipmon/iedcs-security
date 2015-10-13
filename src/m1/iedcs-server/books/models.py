from django.db import models
from django.core.validators import MinLengthValidator
import uuid


class Book(models.Model):
    identifier = models.CharField(max_length=128, default=uuid.uuid4, blank=False, unique=True)

    name = models.CharField(max_length=128, blank=False, validators=[MinLengthValidator(1)])
    production_date = models.DateField()
    author = models.CharField(max_length=128, blank=False, validators=[MinLengthValidator(1)])

    original_file = models.FileField()

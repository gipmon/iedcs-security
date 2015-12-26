from django.db import models
from authentication.models import Account


class ContentCiphered(models.Model):
    identifier = models.CharField(max_length=512, unique=True)
    content = models.TextField()
    user = models.ForeignKey(Account, blank=False)

from django.db import models


class ContentCiphered(models.Model):
    identifier = models.CharField(max_length=512, unique=True)
    content = models.TextField()
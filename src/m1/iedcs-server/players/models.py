from django.db import models
from authentication.models import Account


class Device(models.Model):
    unique_identifier = models.CharField(max_length=254, default="", unique=True)

    owner = models.ForeignKey(Account)

    cpu_model = models.CharField(max_length=128, default="")
    op_system = models.CharField(max_length=128, default="")
    ip = models.CharField(max_length=128, default="")
    country = models.CharField(max_length=128, default="")
    timezone = models.CharField(max_length=128, default="")
    host_name = models.CharField(max_length=128, default="")

    public_key = models.FileField()

    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)

    class Meta:
        unique_together = ('unique_identifier', 'owner',)


class Player(models.Model):
    version = models.CharField(max_length=128, default="", unique=True)
    public_key = models.FileField()
    private_key = models.FileField()
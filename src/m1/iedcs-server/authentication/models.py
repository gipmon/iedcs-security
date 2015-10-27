from django.db import models
from django.contrib.auth.models import AbstractBaseUser, BaseUserManager

from django.core.validators import MinLengthValidator, EmailValidator


class AccountManager(BaseUserManager):
    def create_user(self, email, password=None, **kwargs):
        if not email:
            raise ValueError("User must be have a valid Email Address")
        if not kwargs.get('username'):
            raise ValueError("User must have a valid Username")
        if not kwargs.get('first_name'):
            raise ValueError('User must have a valid First Name')
        if not kwargs.get('last_name'):
            raise ValueError('User must have a valid Last Name')

        user_data = UserCollectedData.objects.create()

        account = self.model(
            email=self.normalize_email(email),
            username=kwargs.get('username'),
            first_name=kwargs.get('first_name'),
            last_name=kwargs.get('last_name'),
            user_data=user_data)

        account.set_password(password)
        account.save()

        return account


class UserCollectedData(models.Model):
    cpu_model = models.CharField(max_length=128, default="")
    op_system = models.CharField(max_length=128, default="")
    ip = models.CharField(max_length=128, default="")
    country = models.CharField(max_length=128, default="")
    timezone = models.CharField(max_length=128, default="")


class Account(AbstractBaseUser):
    email = models.EmailField(unique=True, blank=False, validators=[EmailValidator])
    username = models.CharField(max_length=40, unique=True, blank=False, validators=[MinLengthValidator(2)])

    first_name = models.CharField(max_length=40, validators=[MinLengthValidator(2)])
    last_name = models.CharField(max_length=40, validators=[MinLengthValidator(2)])

    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)


    user_data = models.ForeignKey('UserCollectedData', db_index=True, blank=True)


    objects = AccountManager()

    USERNAME_FIELD = 'email'
    REQUIRED_FIELDS = ['username', 'first_name', 'last_name']

    def __unicode__(self):
        return self.email

    def get_full_name(self):
        return ' '.join([self.first_name, self.last_name])

    def get_short_name(self):
        return self.first_name





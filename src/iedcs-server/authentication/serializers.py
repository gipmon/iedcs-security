from rest_framework import serializers
from authentication.models import Account

from django.core.validators import MinLengthValidator


class AccountSerializer(serializers.ModelSerializer):
    password = serializers.CharField(write_only=True, required=True, validators=[MinLengthValidator(8)])
    confirm_password = serializers.CharField(write_only=True, required=True, validators=[MinLengthValidator(8)])

    class Meta:
        model = Account
        fields = ('email', 'username', 'first_name', 'last_name', 'password', 'confirm_password', 'has_cc',
                  'created_at', 'updated_at',)
        read_only_fields = ('created_at', 'updated_at')


class PasswordSerializer(serializers.ModelSerializer):
    password = serializers.CharField(write_only=True, required=True, validators=[MinLengthValidator(8)])
    confirm_password = serializers.CharField(write_only=True, required=True, validators=[MinLengthValidator(8)])

    class Meta:
        model = Account
        fields = ('password', 'confirm_password',)
        read_only_fields = ()


class AccountPEMSerializer(serializers.ModelSerializer):
    public_key = serializers.CharField(max_length=None, min_length=None, allow_blank=False)
    password = serializers.CharField(required=True)

    class Meta:
        model = Account
        fields = ('public_key', 'first_name', 'last_name', 'citizen_card_serial_number', 'password',)
        read_only_fields = ()


class AccountPEMAuthenticateSerializer(serializers.ModelSerializer):
    random = serializers.CharField(required=True, max_length=None, min_length=None, allow_blank=False)
    sign = serializers.CharField(required=True, max_length=None, min_length=None, allow_blank=False)

    class Meta:
        model = Account
        fields = ('random', 'sign',)
        read_only_fields = ()


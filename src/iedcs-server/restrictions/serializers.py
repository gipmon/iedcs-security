from rest_framework import serializers
from .models import Restriction, BookRestrictions


class BookRestrictionsSerializer(serializers.ModelSerializer):
    book_identifier = serializers.CharField(max_length=128)

    class Meta:
        model = BookRestrictions
        fields = ('book_identifier',)
        read_only_fields = ('book_identifier',)


from rest_framework import serializers
from .models import Book


class BookSerializer(serializers.ModelSerializer):

    class Meta:
        model = Book
        fields = ('identifier', 'name', 'production_date', 'author')
        read_only_fields = ('identifier', 'name', 'production_date', 'author')


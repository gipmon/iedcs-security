from rest_framework import serializers
from .models import Book, OrderBook


class BookSerializer(serializers.ModelSerializer):

    class Meta:
        model = Book
        fields = ('identifier', 'name', 'production_date', 'author')


class MakeOrderSerializer(serializers.ModelSerializer):
    book_identifier = serializers.CharField(max_length=128)

    class Meta:
        model = OrderBook
        fields = ('book_identifier',)
        read_only_fields = ('book_identifier',)

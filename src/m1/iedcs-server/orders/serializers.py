from rest_framework import serializers
from .models import Order, OrderBook
from books.serializers import BookSerializer


class OrderSerializer(serializers.ModelSerializer):
    book = OrderBookSerializer(many=True)

    class Meta:
        model = Order
        fields = ('identifier', 'buyer', 'books',)
        read_only_fields = ('identifier', 'buyer', 'books',)


class OrderBookSerializer(serializers.ModelSerializer):
    book = BookSerializer()

    class Meta:
        model = OrderBook
        fields = ('book',)
        read_only_fields = ('book',)


class MakeOrderSerializer(serializers.ModelSerializer):
    books_identifier = serializers.CharField(max_length=128, many=True)

    class Meta:
        model = Order
        fields = ('books_identifier',)
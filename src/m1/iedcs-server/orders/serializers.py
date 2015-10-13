from rest_framework import serializers
from .models import Order, OrderBook
from books.serializers import BookSerializer
from rest_framework.validators import ValidationError


class OrderBookSerializer(serializers.ModelSerializer):
    book = BookSerializer()

    class Meta:
        model = OrderBook
        fields = ('book',)
        read_only_fields = ('book',)


class OrderSerializer(serializers.ModelSerializer):
    books = OrderBookSerializer(many=True)

    class Meta:
        model = Order
        fields = ('identifier', 'buyer', 'books',)
        read_only_fields = ('identifier', 'buyer', 'books',)


class MakeOrderSerializer(serializers.ModelSerializer):
    books_identifier = serializers.ListField(child=serializers.CharField(), required=True, allow_null=False)

    class Meta:
        model = Order
        fields = ('books_identifier',)


class MakeOrderSerializer(serializers.BaseSerializer):
    def to_representation(self, instance):
        pass

    def create(self, validated_data):
        pass

    def update(self, instance, validated_data):
        pass

    def to_internal_value(self, data):
        books_identifier = data.get('books_identifier')

        # Perform the data validation.
        if not books_identifier:
            raise ValidationError({
                'message': 'This field is required1.'
            })

        print books_identifier
        
        if type(books_identifier) is not list:
            raise ValidationError({
                'message': 'This field is required2.'
            })

        for book_identifier in books_identifier:
            if type(book_identifier) is not str:
                raise ValidationError({
                    'message': 'This field is required3.'
                })

        # Return the validated values. This will be available as
        # the `.validated_data` property.
        return {
            'books_identifier': books_identifier
        }
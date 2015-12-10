from rest_framework import serializers
from .models import File
from books.serializers import BookSerializer


class FileSerializer(serializers.ModelSerializer):
    book = BookSerializer()

    class Meta:
        model = File
        fields = ('identifier', 'book',)
        read_only_fields = ('identifier', 'book',)

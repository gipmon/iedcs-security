from django.shortcuts import get_object_or_404
from rest_framework import viewsets, mixins
from rest_framework.response import Response
from .models import Book
from .serializers import BookSerializer


class BooksViewSet(mixins.ListModelMixin, mixins.RetrieveModelMixin, viewsets.GenericViewSet):
    queryset = Book.objects.filter()
    serializer_class = BookSerializer

    def list(self, request, *args, **kwargs):
        """
        B{List} the books
        B{URL:} ../api/v1/books/
        """
        serializer = self.serializer_class(self.queryset, many=True)
        return Response(serializer.data)

    def retrieve(self, request, *args, **kwargs):
        """
        B{Retrieve} the book details
        B{URL:} ../api/v1/books/<identifier>/

        :type  identifier: str
        :param identifier: The identifier
        """
        book = get_object_or_404(Book.objects.all(), identifier=kwargs.get('pk', ''))
        serializer = self.serializer_class(book)
        return Response(serializer.data)

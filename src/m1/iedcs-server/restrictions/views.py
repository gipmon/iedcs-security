from django.shortcuts import get_object_or_404
from django.db import IntegrityError
from rest_framework.response import Response
from .models import Book, BookRestrictions
from books.models import OrderBook
from .serializers import BookRestrictionsSerializer
from rest_framework import permissions
from rest_framework import viewsets, status, mixins, views
from .restrictions import test_restriction
from django.db import transaction
import json


class BooksRestrictionsViewSet(mixins.CreateModelMixin, viewsets.GenericViewSet):
    queryset = BookRestrictions.objects.filter()
    serializer_class = BookRestrictionsSerializer

    def get_permissions(self):
        return permissions.IsAuthenticated(),

    def create(self, request, *args, **kwargs):
        """
        Verify a book restriction
        B{URL:} ../api/v1/book_restrictions/

        :type  books_identifier: str
        :param books_identifier: the books that the user wants to read
        """
        serializer = BookRestrictionsSerializer(data=request.data)

        if serializer.is_valid():
            user = request.user
            # verify if the book exists
            book = get_object_or_404(Book.objects.all(), identifier=serializer.validated_data['book_identifier'])

            # verify if the user already purchase the book
            if OrderBook.objects.filter(buyer=user, book=book).count() != 1:
                return Response({'status': 'Bad Request',
                                 'message': 'You don\'t have purchased the book: ' + book.name},
                                status=status.HTTP_400_BAD_REQUEST)

            can = True

            for book_restriction in BookRestrictions.objects.filter(book=book):
                can &= test_restriction(book_restriction.restriction.restrictionFunction, book, user.user_data)

            return Response(json.dumps(["restricted": ), status=status.HTTP_201_CREATED)

        return Response({'status': 'Bad Request',
                         'message': serializer.errors},
                        status=status.HTTP_400_BAD_REQUEST)


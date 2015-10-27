from django.shortcuts import get_object_or_404
from django.db import IntegrityError
from rest_framework.response import Response
from .models import Book, OrderBook
from .serializers import BookSerializer, MakeOrderSerializer
from .renders import PlainTextRenderer
from rest_framework import permissions
from rest_framework import viewsets, status, mixins, views
from django.db import transaction


class DeviceViewSet(mixins.ListModelMixin, mixins.RetrieveModelMixin,
                    mixins.CreateModelMixin, viewsets.GenericViewSet):
    queryset = OrderBook.objects.filter()
    serializer_class = BookSerializer

    def get_permissions(self):
        return permissions.IsAuthenticated(),

    def list(self, request, *args, **kwargs):
        """
        B{List} user devices
        B{URL:} ../api/v1/devices/
        """
        order_books = OrderBook.objects.filter(buyer=request.user)

        books = [order_book.book for order_book in order_books]

        serializer = self.serializer_class(books, many=True)
        return Response(serializer.data)

    def retrieve(self, request, *args, **kwargs):
        """
        B{Retrieve} if the unique identifier is already registed for that user
        B{URL:} ../api/v1/devices/<unique_identifier>/

        :type  unique_identifier: str
        :param unique_identifier: The identifier
        """
        book = get_object_or_404(Book.objects.all(), identifier=kwargs.get('pk', ''))
        serializer = self.serializer_class(book)
        return Response(serializer.data)

    def create(self, request, *args, **kwargs):
        """
        B{Create} a device
        B{URL:} ../api/v1/devices/

        :type  books_identifier: str
        :param books_identifier: the books that the user wants to purchase
        """
        serializer = MakeOrderSerializer(data=request.data)

        if serializer.is_valid():
            try:
                user = request.user
                # verify if the book exists
                book = get_object_or_404(Book.objects.all(), identifier=serializer.validated_data['book_identifier'])

                # verify if the user already purchase the book
                if OrderBook.objects.filter(buyer=user, book=book).count() == 1:
                    return Response({'status': 'Bad Request',
                                     'message': 'You already purchase the book: ' + book.name},
                                    status=status.HTTP_400_BAD_REQUEST)

                with transaction.atomic():
                    # new order
                    OrderBook.objects.create(buyer=user, book=book)
                    return Response(serializer.data, status=status.HTTP_201_CREATED)

            except IntegrityError:
                return Response({'status': 'Bad request',
                                 'message': 'The book couldn\'t be purchase!'},
                                status=status.HTTP_400_BAD_REQUEST)

        return Response({'status': 'Bad Request',
                         'message': serializer.errors},
                        status=status.HTTP_400_BAD_REQUEST)

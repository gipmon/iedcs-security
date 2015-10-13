from django.shortcuts import get_object_or_404
from django.db import IntegrityError
from django.db import transaction
from rest_framework import viewsets, status, mixins
from rest_framework.response import Response
from .models import Order, OrderBook
from .serializers import OrderSerializer, MakeOrderSerializer
from books.models import Book


class OrderViewSet(mixins.ListModelMixin, mixins.RetrieveModelMixin, mixins.CreateModelMixin, viewsets.GenericViewSet):
    queryset = Order.objects.filter()
    serializer_class = OrderSerializer

    def list(self, request, *args, **kwargs):
        """
        B{List} the orders of the user
        B{URL:} ../api/v1/order/
        """
        #files = get_list_or_404(Order.objects.all(), buyer=request.user)
        #serializer = self.serializer_class(files, many=True)
        #return Response(serializer.data)
        pass

    def create(self, request, *args, **kwargs):
        """
        B{Create} an order
        B{URL:} ../api/v1/order/

        :type  books_identifier: str
        :param books_identifier: the books that the user wants to purchase
        """
        serializer = MakeOrderSerializer(data=request.data)

        if serializer.is_valid():
            try:
                user = request.user

                for book_identifier in serializer.validated_data['books_identifier']:
                    # verify if the book exists
                    book = get_object_or_404(Book.objects.all(), identifier=book_identifier)

                    # verify if the user already purchase the book
                    for order in user.order_set.all():
                        for order_book in order.orderbook_set.all():
                            if order_book.book.identifier == book_identifier:
                                return Response({'status': 'Bad Request',
                                                 'message': 'You already purchase the book: ' + book.name},
                                                status=status.HTTP_400_BAD_REQUEST)

                with transaction.atomic():
                    # new order
                    order = Order.objects.create(buyer=user)

                    # associate the books to the order
                    for book_identifier in serializer.validated_data['books_identifier']:
                        book = get_object_or_404(Book.objects.all(), identifier=book_identifier)
                        OrderBook.objects.create(order=order, book=book)

            except IntegrityError:
                return Response({'status': 'Bad request',
                                 'message': 'The book couldn\'t be purchase!'},
                                status=status.HTTP_400_BAD_REQUEST)

            serializer = self.serializer_class(order)
            return Response(serializer.data, status=status.HTTP_201_CREATED)

        return Response({'status': 'Bad Request',
                         'message': serializer.errors},
                        status=status.HTTP_400_BAD_REQUEST)

    def retrieve(self, request, *args, **kwargs):
        pass

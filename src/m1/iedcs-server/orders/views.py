from django.shortcuts import get_object_or_404, get_list_or_404
from django.db import IntegrityError
from django.db import transaction
from rest_framework import viewsets, status, mixins
from rest_framework.response import Response
from .models import Order, OrderBook
from .serializers import OrderSerializer, MakeOrderSerializer, OrderBookSerializer
from books.models import Book
from rest_framework import permissions
from .simplex import OrderSimplex


class OrderViewSet(mixins.ListModelMixin, mixins.RetrieveModelMixin, mixins.CreateModelMixin, viewsets.GenericViewSet):
    queryset = Order.objects.filter()
    serializer_class = OrderSerializer

    def get_permissions(self):
        return permissions.IsAuthenticated(),

    def list(self, request, *args, **kwargs):
        """
        B{List} the orders of the user
        B{URL:} ../api/v1/orders/
        """
        orders = get_list_or_404(Order.objects.all(), buyer=request.user)

        orders_simples = []

        for order in orders:
            orders_simples += [OrderSimplex(order.identifier, order.buyer, order.orderbook_set.all())]

        serializer = self.serializer_class(orders_simples, many=True)
        return Response(serializer.data)

    def create(self, request, *args, **kwargs):
        """
        B{Create} an order
        B{URL:} ../api/v1/orders/

        :type  books_identifier: str
        :param books_identifier: the books that the user wants to purchase
        """
        if not request.POST:
            return Response({'status': 'Bad Request',
                             'message': ''},
                            status=status.HTTP_400_BAD_REQUEST)

        serializer = MakeOrderSerializer(data=request.POST)

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

                    serializer = self.serializer_class(OrderSimplex(order.identifier, order.buyer,
                                                                    order.orderbook_set.all()))

                    return Response(serializer.data, status=status.HTTP_201_CREATED)

            except IntegrityError:
                return Response({'status': 'Bad request',
                                 'message': 'The book couldn\'t be purchase!'},
                                status=status.HTTP_400_BAD_REQUEST)

        return Response({'status': 'Bad Request',
                         'message': serializer.errors},
                        status=status.HTTP_400_BAD_REQUEST)

    def retrieve(self, request, *args, **kwargs):
        """
        B{Retrieve} the sticky note represented by the identifier
        B{URL:} ../api/v1/orders/<identifier>/

        :type  identifier: str
        :param identifier: The identifier
        """
        order = get_object_or_404(Order.objects.all(), identifier=kwargs.get('pk', ''))

        if order.buyer != request.user:
                return Response({'status': 'Bad request',
                                 'message': 'Nothing here!'},
                                status=status.HTTP_403_FORBIDDEN)

        serializer = OrderBookSerializer(order.orderbook_set.all(), many=True)
        return Response(serializer.data)
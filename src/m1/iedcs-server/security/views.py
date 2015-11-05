from django.shortcuts import get_object_or_404
from rest_framework.response import Response
from rest_framework import permissions
from rest_framework import viewsets, status, mixins
from .serializers import ExchangeRd1Rd2Serializer
from books.models import Book, OrderBook
from players.models import Device, DeviceOwner
from security.functions import get_database_content_by_user_and_book, rd2_process
import base64


class ExchangeRd1Rd2ViewSet(mixins.CreateModelMixin, viewsets.GenericViewSet):
    queryset = Device.objects.filter()
    serializer_class = ExchangeRd1Rd2Serializer

    def get_permissions(self):
        return permissions.IsAuthenticated(),

    def create(self, request, *args, **kwargs):
        """
        B{Create} decipher process
        B{URL:} ../api/v1/security_exchange_r1r2/

        :type  book_identifier: str
        :param book_identifier: the book unique identifier
        :type  rd1: str
        :param rd1: the random1
        :type  device_identifier: str
        :param device_identifier: the device identifier

        """
        serializer = ExchangeRd1Rd2Serializer(data=request.data)

        if serializer.is_valid():
            book = get_object_or_404(Book.objects.all(), identifier=serializer.data["book_identifier"])
            device = get_object_or_404(Device.objects.all(), unique_identifier=serializer.data["device_identifier"])
            get_object_or_404(DeviceOwner.objects.all(), owner=request.user, device=device)
            get_object_or_404(OrderBook.objects.all(), buyer=request.user, book=book)

            random1 = get_database_content_by_user_and_book("random1", request.user, book)

            return Response({'rd2': rd2_process(serializer.data["rd1"], request.user, book, random1)})

        return Response({'status': 'Bad Request',
                         'message': serializer.errors},
                        status=status.HTTP_400_BAD_REQUEST)

from django.shortcuts import get_object_or_404
from django.db import IntegrityError
from rest_framework.response import Response
from .models import Device, DeviceOwner
from .serializers import CreateDeviceSerializer, DeviceSerializer, UpdateDeviceSerializer, DeviceRetrieveSerializer
from rest_framework import permissions
from rest_framework import viewsets, status, mixins
from django.db import transaction
import binascii
import hashlib
from django.core.files.storage import default_storage
from iedcs.settings import BASE_DIR
from django.core.files.base import ContentFile
from geoip import geolite2


class DeviceViewSet(mixins.ListModelMixin, mixins.RetrieveModelMixin, mixins.UpdateModelMixin,
                    mixins.CreateModelMixin, viewsets.GenericViewSet):
    queryset = Device.objects.filter()
    serializer_class = DeviceSerializer

    def get_permissions(self):
        return permissions.IsAuthenticated(),

    def list(self, request, *args, **kwargs):
        """
        B{List} user devices
        B{URL:} ../api/v1/devices/
        """
        devices = DeviceOwner.objects.filter(owner=request.user)
        serializer = self.serializer_class([device.device for device in devices], many=True)
        return Response(serializer.data)


    def create(self, request, *args, **kwargs):
        """
        B{Create} a device
        B{URL:} ../api/v1/devices/

        :type  unique_identifier: str
        :param unique_identifier: the device unique identifier
        :type  host_name: str
        :param host_name: the host name
        :type  cpu_model: str
        :param cpu_model: the device cpu model
        :type  op_system: str
        :param op_system: the device op system
        :type  ip: str
        :param ip: the device ip
        :type  country: str
        :param country: the device country
        :type  timezone: str
        :param timezone: the device timezone
        :type  public_key: str
        :param public_key: the public_key
        """
        serializer = CreateDeviceSerializer(data=request.data)

        if serializer.is_valid():
            public_key = serializer.data["public_key"]
            byte_key = binascii.a2b_base64(public_key)

            m = hashlib.md5()
            m.update(serializer.data["unique_identifier"])
            folder_name = m.hexdigest()

            path = default_storage.save(BASE_DIR+'/media/devices/' + folder_name + '/device_pub.key',
                                        ContentFile(byte_key))

            c = geolite2.lookup(serializer.data["ip"]).country
            try:
                with transaction.atomic():
                    if Device.objects.filter(unique_identifier=serializer.data["unique_identifier"]).count() is 1:
                        device = Device.objects.get(unique_identifier=serializer.data["unique_identifier"])

                        if DeviceOwner.objects.filter(device=device, owner=request.user).count() is 1:
                            return Response({'status': 'Already created',
                                             'message': 'The device has been already registered'},
                                            status=status.HTTP_400_BAD_REQUEST)
                        else:
                            DeviceOwner.objects.create(device=device, owner=request.user)

                            return Response({'status': 'Associated',
                                             'message': 'The device has been associated'},
                                            status=status.HTTP_201_CREATED)
                    else:
                        device = Device.objects.create(unique_identifier=serializer.data["unique_identifier"],
                                                       cpu_model=serializer.data["cpu_model"],
                                                       op_system=serializer.data["op_system"],
                                                       ip=serializer.data["ip"],
                                                       country=c,
                                                       timezone=serializer.data["timezone"],
                                                       host_name=serializer.data["host_name"],
                                                       public_key=path)

                        DeviceOwner.objects.create(device=device, owner=request.user)

                        return Response({'status': 'Created',
                                         'message': 'The device has been registered'},
                                        status=status.HTTP_201_CREATED)

            except IntegrityError:
                return Response({'status': 'Bad request',
                                 'message': 'The device can\'t be added!'},
                                status=status.HTTP_400_BAD_REQUEST)

        return Response({'status': 'Bad Request',
                         'message': serializer.errors},
                        status=status.HTTP_400_BAD_REQUEST)

    def update(self, request, *args, **kwargs):
        """
        B{Update} the device
        B{URL:} ../api/v1/devices/<random_string>/

        :type  unique_identifier: str
        :param unique_identifier: the unique identifier
        :type  host_name: str
        :param host_name: the host name
        :type  cpu_model: str
        :param cpu_model: the device cpu model
        :type  op_system: str
        :param op_system: the device op system
        :type  ip: str
        :param ip: the device ip
        :type  country: str
        :param country: the device country
        :type  timezone: str
        :param timezone: the device timezone
        """
        serializer = UpdateDeviceSerializer(data=request.data)

        if serializer.is_valid():
            device = get_object_or_404(Device.objects.all(), unique_identifier=serializer.validated_data['unique_identifier'])
            get_object_or_404(DeviceOwner.objects.all(), owner=request.user, device=device)

            device.cpu_model = serializer.validated_data['cpu_model']
            device.op_system = serializer.validated_data['op_system']
            device.ip = serializer.validated_data['ip']
            device.timezone = serializer.validated_data['timezone']
            device.host_name = serializer.validated_data['host_name']
            device.save()

            return Response({'status': 'Updated',
                            'message': 'The device has been updated.'},
                            status=status.HTTP_200_OK)

        else:
            return Response({'status': 'Bad request',
                             'message': serializer.errors},
                            status=status.HTTP_400_BAD_REQUEST)


class DeviceRetrieveView(mixins.CreateModelMixin, viewsets.GenericViewSet):
    queryset = Device.objects.filter()
    serializer_class = DeviceSerializer

    def get_permissions(self):
        return permissions.IsAuthenticated(),

    def create(self, request, *args, **kwargs):
        """
        B{Retrieve} a device
        B{URL:} ../api/v1/retrieveDevice/

        :type  unique_identifier: str
        :param unique_identifier: the device unique identifier
        """

        serializer=DeviceRetrieveSerializer(data=request.data)
        if serializer.is_valid():

            device = get_object_or_404(Device.objects.all(), unique_identifier=serializer.data["unique_identifier"])
            get_object_or_404(DeviceOwner.objects.all(), owner=request.user, device=device)
            serializer = self.serializer_class(device)
            return Response(serializer.data)

        return Response({'status': 'Bad Request',
                         'message': serializer.errors},
                        status=status.HTTP_400_BAD_REQUEST)

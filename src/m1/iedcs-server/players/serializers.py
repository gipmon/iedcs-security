from rest_framework import serializers
from .models import Device


class CreateDeviceSerializer(serializers.ModelSerializer):
    public_key = serializers.CharField(max_length=254, required=False)

    class Meta:
        model = Device
        fields = ('unique_identifier', 'cpu_model', 'op_system', 'ip', 'country', 'timezone', 'host_name', 'public_key',)


class DeviceSerializer(serializers.ModelSerializer):

    class Meta:
        model = Device
        fields = ('unique_identifier', 'cpu_model', 'op_system', 'ip', 'country', 'timezone', 'host_name',)


class DeviceRetrieveSerializer(serializers.ModelSerializer):
    unique_identifier = serializers.CharField(max_length=254)

    class Meta:
        model = Device
        fields = ('unique_identifier', )
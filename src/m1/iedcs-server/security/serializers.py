from rest_framework import serializers

class ExchangeRd1Rd2Serializer(serializers.ModelSerializer):
    book_identifier = serializers.CharField(max_length=128)
    rd1 = serializers.CharField(max_length=128)
    device_identifier = serializers.CharField(max_length=128)

    class Meta:
        fields = ('book_identifier', 'rd1', 'device_identifier')


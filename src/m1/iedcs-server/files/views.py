from django.shortcuts import get_list_or_404
from rest_framework import viewsets, mixins
from rest_framework.response import Response
from .models import File
from .serializers import FileSerializer


class UserFiles(mixins.ListModelMixin, viewsets.GenericViewSet):
    queryset = File.objects.filter()
    serializer_class = FileSerializer

    def list(self, request, *args, **kwargs):
        """
        B{List} the user files
        B{URL:} ../api/v1/files/user/
        """
        files = get_list_or_404(File.objects.all(), buyer=request.user)
        serializer = self.serializer_class(files, many=True)
        return Response(serializer.data)

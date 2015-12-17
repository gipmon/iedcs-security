import json
import hashlib
from django.core.files.base import ContentFile

from django.shortcuts import get_object_or_404
from rest_framework import mixins, viewsets, views, status, permissions
from rest_framework.response import Response
from django.contrib.auth import authenticate, login, logout
import rsa
import base64

from .permissions import UserIsUser, IsAccountOwner
from .models import Account
from .serializers import AccountSerializer, PasswordSerializer, AccountPEMSerializer, AccountPEMAuthenticateSerializer

import uuid


class AccountViewSet(mixins.CreateModelMixin, viewsets.GenericViewSet):
    """
    ## Create an user
    - #### Method: **POST**
    - #### URL: **/api/v1/accounts/**
    - #### Parameters: email, username, first_name, last_name, password, confirm_password
    - #### Permissions: **Allow Any**
    """
    lookup_field = 'username'
    queryset = Account.objects.all()
    serializer_class = AccountSerializer

    def get_permissions(self):
        if self.request.method == 'POST':
            return permissions.AllowAny(),

        if self.request.method in permissions.SAFE_METHODS:
            return permissions.AllowAny(),

        return permissions.IsAuthenticated(), IsAccountOwner(),

    def create(self, request, **kwargs):
        """
        B{Create} an user
        :param **kwargs:
        B{URL:} ../api/v1/accounts/

        :type  email: str
        :param email: email
        :type  username: str
        :param username: username
        :type  first_name: str
        :param first_name: The first name of user
        :type  last_name: str
        :param last_name: The last name of user
        :type  password: str
        :param password: The password of user
        :type  confirm_password: str
        :param confirm_password: The password confirmation
        """
        serializer = self.serializer_class(data=request.data)

        if serializer.is_valid():
            Account.objects.create_user(**serializer.validated_data)
            return Response(serializer.validated_data, status=status.HTTP_201_CREATED)

        return Response({'status': 'Bad Request',
                         'message': serializer.errors
                         }, status=status.HTTP_400_BAD_REQUEST)


class AccountChangePassword(mixins.UpdateModelMixin, viewsets.GenericViewSet):
    """
    ## Change password
    - #### Method: **PUT**
    - #### URL: **/api/v1/change_password/&lt;username&gt;/**
    - #### Parameters: Password and confirm_password
    - #### Permissions: **Is Authenticated and Is Account Owner**
    """
    lookup_field = 'username'
    queryset = Account.objects.all()
    serializer_class = PasswordSerializer

    def get_permissions(self):
        return permissions.IsAuthenticated(), IsAccountOwner(),

    def update(self, request, *args, **kwargs):
        """
        B{Update} the password
        B{URL:} ../api/v1/change_password/<username>/

        -> Permissions
        # update
            UserIsUser

        :type  password: str
        :param password: The password
        :type  confirm_password: str
        :param confirm_password: The confirmation password
        """
        instance = get_object_or_404(Account.objects.all(), username=kwargs.get('username', ''))

        UserIsUser(user=request.user, instance=instance, message="Ups, what?")

        serializer = self.serializer_class(data=request.data)

        if serializer.is_valid():
            password = request.data.get('password', None)
            confirm_password = request.data.get('confirm_password', None)

            if password and confirm_password and password == confirm_password:
                instance.set_password(password)
                instance.save()

            return Response({'status': 'Updated',
                             'message': 'Account updated.'
                             }, status=status.HTTP_200_OK)

        return Response({'status': 'Bad Request',
                         'message': serializer.errors
                         }, status=status.HTTP_400_BAD_REQUEST)


class LoginView(views.APIView):
    """
    ## Login to the platform
    - #### Method: **POST**
    - #### Parameters: **email, password**
    - #### URL: **/api/v1/auth/login/**
    - #### Permissions: **Allow any**
    """
    def post(self, request):
        """
        B{Login} an user
        B{URL:} ../api/v1/auth/login/
        """
        data = json.loads(request.body)
        email = data.get('email', None)
        password = data.get('password', None)

        # get user
        accounts = Account.objects.filter(email=email)

        if accounts.count() == 0:
            return Response({'status': 'Unauthorized',
                             'message': 'Username and/or password is wrong.'
                             }, status=status.HTTP_401_UNAUTHORIZED)

        account = authenticate(email=email, password=password)

        if account is not None:
            if not data.get('remember_me', None):
                request.session.set_expiry(0)

            login(request, account)

            serialized = AccountSerializer(account)

            return Response(serialized.data)
        else:
            return Response({'status': 'Unauthorized',
                             'message': 'Username and/or password is wrong.'
                             }, status=status.HTTP_401_UNAUTHORIZED)


class LogoutView(views.APIView):
    """
    ## Logout from the platform
    - #### Method: **POST**
    - #### URL: **/api/v1/auth/logout/**
    - #### Permissions: **Is authenticated**
    """
    def get_permissions(self):
        return permissions.IsAuthenticated(),

    def post(self, request):
        """
        B{Logout} an user
        B{URL:} ../api/v1/auth/logout/
        """
        logout(request)

        return Response({}, status=status.HTTP_204_NO_CONTENT)


class MyDetails(views.APIView):
    """
    ## See the details of the current logged user
    - #### Method: **GET**
    - #### URL: **/api/v1/me/**
    - #### Permissions: **Is authenticated**
    """
    queryset = Account.objects.all()
    serializer_class = AccountSerializer

    def get_permissions(self):
        return permissions.IsAuthenticated(),

    def get(self, request):
        """
        See the details of the current logged user
        B{URL:} ../api/v1/me/
        """
        serializer = self.serializer_class(request.user)
        return Response(serializer.data)


class SavePEMCitizenAuthentication(mixins.CreateModelMixin, viewsets.GenericViewSet):
    queryset = Account.objects.filter()
    serializer_class = AccountPEMSerializer

    def get_permissions(self):
        return permissions.IsAuthenticated(),

    def create(self, request, *args, **kwargs):
        """
        B{Create} a device
        B{URL:} ../api/v1/player/citizen_authentication/

        :type  public_key: str
        :param public_key: the public_key
        """
        serializer = AccountPEMSerializer(data=request.data)

        if serializer.is_valid():
            public_key = serializer.data["public_key"]

            account = authenticate(email=request.user.email, password=serializer.data["password"])

            if account is not None:
                request.user.citizen_card.save(str(uuid.uuid4()) + ".pub", ContentFile(public_key))
                request.user.has_cc = True
                request.user.first_name = serializer.data["first_name"]
                request.user.last_name = serializer.data["last_name"]
                request.user.citizen_card_serial_number = serializer.data["citizen_card_serial_number"]
                request.user.save()

                return Response({'status': 'Good request',
                                 'message': 'The citizen card has been added!'},
                                status=status.HTTP_200_OK)
            else:
                return Response({'status': 'Bad Request',
                                 'message': {"password_wrong": ["The password is wrong!"]}},
                                status=status.HTTP_400_BAD_REQUEST)

        return Response({'status': 'Bad Request',
                         'message': serializer.errors},
                        status=status.HTTP_400_BAD_REQUEST)


class CitizenAuthenticate(views.APIView):
    """
    B{Create} a device
    B{URL:} ../api/v1/player/citizen_authenticate/

    :type  random: str
    :param random: the random
    :type  sign: str
    :param sign: the random sign
    :type  citizen_card_serial_number: str
    :param citizen_card_serial_number: citizen_card_serial_number
    """
    def post(self, request):
        serializer = AccountPEMAuthenticateSerializer(data=request.data)

        if serializer.is_valid():
            if Account.objects.filter(citizen_card_serial_number=serializer.data["citizen_card_serial_number"]).count() == 1:
                user = Account.objects.get(citizen_card_serial_number=serializer.data["citizen_card_serial_number"])

                random = serializer.data["random"]
                pub_key = rsa.PublicKey.load_pkcs1(user.citizen_card.read())

                try:
                    rsa.verify(random, base64.b64decode(serializer.data["sign"]), pub_key)
                    user.backend = 'django.contrib.auth.backends.ModelBackend'
                    login(request, user)
                    serialized = AccountSerializer(user)
                    return Response(serialized.data)
                except rsa.pkcs1.VerificationError, e:
                    pass
        return Response({'status': 'Unauthorized',
                         'message': 'The citizen card that you have is not associated with any account!'
                         }, status=status.HTTP_401_UNAUTHORIZED)


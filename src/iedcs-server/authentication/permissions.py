from rest_framework import permissions
from iedcs.exceptions import Forbidden


class IsAccountOwner(permissions.BasePermission):
    def has_object_permission(self, request, view, obj):
        if request.user:
            return obj == request.user

        return False


class UserIsUser:
    def __init__(self, user, instance, message):
        if instance != user:
            raise Forbidden(message)

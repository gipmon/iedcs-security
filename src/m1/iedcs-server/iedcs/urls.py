from django.conf.urls import patterns, include, url
from django.views.generic.base import TemplateView

from authentication.views import AccountViewSet, LoginView, LogoutView, AccountChangePassword, MyDetails
from books.views import BooksViewSet, OrderViewSet, BookView
from files.views import UserFiles
from rest_framework import routers


router_base = routers.SimpleRouter()
router_base.register(r'accounts', AccountViewSet)
router_base.register(r'change_password', AccountChangePassword)
router_base.register(r'books', BooksViewSet)
router_base.register(r'user_books', OrderViewSet)

router_files = routers.SimpleRouter()
router_files.register(r'user', UserFiles)

urlpatterns = patterns('',
                       url(r'^api/v1/', include(router_base.urls)),
                       url(r'^api/v1/get_book/(?P<identifier>.+)/$',
                           BookView.as_view(),
                           name="Get json round file"),
                       url(r'^api/v1/files/', include(router_files.urls)),
                       url(r'^api/v1/me/$', MyDetails.as_view(), name="ME"),

                       url(r"api/v1/auth/login/$", LoginView.as_view(), name="login"),
                       url(r'^api/v1/auth/logout/$', LogoutView.as_view(), name='logout'),
                       url(r'^api-auth/', include('rest_framework.urls', namespace='rest_framework')),
                       url('^.*$', TemplateView.as_view(template_name='index.html'), name='index')
                       )
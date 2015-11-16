from django.test import TestCase
from authentication.models import Account, UserCollectedData
from rest_framework.test import APIClient


class AuthenticationTestCase(TestCase):
    def setUp(self):
        self.ucd1 = UserCollectedData.objects.create(cpu_model="MacBook Pro", op_system="MacOS", ip="193.2.4.1",
                                                     country="PT")
        Account.objects.create(email='test@test.com', username='test', first_name='unit', last_name='test', user_data=self.ucd1)

    def test_account_details(self):
        account = Account.objects.get(email='test@test.com')
        self.assertEqual(account.email, 'test@test.com')
        self.assertEqual(account.username, 'test')
        self.assertEqual(account.first_name, 'unit')
        self.assertEqual(account.last_name, 'test')

    def test_create_account(self):
        user = Account.objects.get(email='test@test.com')

        client = APIClient()
        client.force_authenticate(user=user)

        # test if the users cant see the list of users
        url = "/api/v1/accounts/"
        response = client.get(url)
        self.assertEqual(response.data, {"detail":"Method \"GET\" not allowed."})

        # create user
        url = "/api/v1/accounts/"
        data = {'email': 'test1@test.com', 'username': 'test1', 'password': 'rei12345678',
                'confirm_password': 'rei12345678', 'first_name': 'unit', 'last_name': 'test'}
        response = client.post(path=url, data=data, format='json')
        self.assertEqual(response.status_code, 201)

        # change password with other user
        url = "/api/v1/change_password/test1/"
        data = {'password': '1234', 'confirm_password': '1234'}
        response = client.put(url, data)
        self.assertEqual(response.data, {u'detail': u'Ups, what?'})
        self.assertEqual(response.status_code, 403)

        # change password with the user
        url = "/api/v1/change_password/test/"
        data = {'password': '1234', 'confirm_password': '1234'}
        response = client.put(url, data)
        self.assertEqual(response.data, {'status': 'Bad Request', 'message': {
            'confirm_password': [u'Ensure this value has at least 8 characters (it has 4).'],
            'password': [u'Ensure this value has at least 8 characters (it has 4).']}})
        self.assertEqual(response.status_code, 400)

        # change correctly the password
        url = "/api/v1/change_password/test/"
        data = {'password': '123456789', 'confirm_password': '123456789'}
        response = client.put(url, data)
        self.assertEqual(response.data, {'status': 'Updated', 'message': 'Account updated.'})
        self.assertEqual(response.status_code, 200)

        # see if the user is currently logged in
        url = "/api/v1/me/"
        response = client.get(url)
        self.assertEqual(response.status_code, 200)

        # see if the total number of users are correct
        self.assertEqual(Account.objects.count(), 2)

        client.logout()

        client.force_authenticate(user=None)
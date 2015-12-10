(function () {
    'use strict';

    angular
        .module("webstore.authentication.services")
        .factory("Authentication", Authentication);

    Authentication.$inject = ["$cookies", "$http"];

    function Authentication($cookies, $http){
        var Authentication = {
            getAuthenticatedAccount: getAuthenticatedAccount,
            isAuthenticated: isAuthenticated,
            register: register,
            login: login,
            logout: logout,
            setAuthenticatedAccount: setAuthenticatedAccount,
            unauthenticate: unauthenticate

        };

        return Authentication;

        function login(email, password){
            return $http.post("/api/v1/auth/login/", {
                email: email,
                password: password
            });
        }

        function register(email,username, first_name, last_name, password, confirm_password){
            return $http.post("api/v1/accounts/",{
                email: email,
                password: password,
                confirm_password: confirm_password,
                username: username,
                first_name: first_name,
                last_name: last_name

            });
        }

        function setAuthenticatedAccount(account){
            $cookies.authenticatedAccount = JSON.stringify(account);
        }

        function getAuthenticatedAccount() {
            if (!$cookies.authenticatedAccount) {
                return;
            }

            return JSON.parse($cookies.authenticatedAccount);
        }

        function isAuthenticated(){
            return !!$cookies.authenticatedAccount;
        }

        function unauthenticate() {
            delete $cookies.authenticatedAccount;
        }

        function logout(){
            return $http.post("api/v1/auth/logout/");
        }


    }
})();
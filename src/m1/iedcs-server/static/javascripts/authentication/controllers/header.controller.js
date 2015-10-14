(function (){
    'use strict';

    angular
        .module('webstore.authentication.controllers')
        .controller('HeaderController', HeaderController);

    HeaderController.$inject = ['$location', 'Authentication', '$scope'];

    function HeaderController($location, Authentication, $scope){
        var header = this;

        header.login = login;
        header.register = register;


        activate();

        function activate(){
            $scope.loader = {
                loading: false
            };
            if(Authentication.isAuthenticated()){
                header.account = Authentication.getAuthenticatedAccount();
                header.logged = true;
                header.hide = false;


            }else{
                header.logged = false;
                header.hide = true;
            }
            $scope.loader = {
                loading: true
            };


        }

        function login(){

            Authentication.login(header.email1, header.password1)
                .then(loginSuccess, loginError);

        }

        function loginafterRegister(email, password){

            Authentication.login(email, password)
                .then(loginSuccess, loginError);

        }

        function loginSuccess(data){

            Authentication.setAuthenticatedAccount(data.data);

            header.logged = true;
            header.hide = false;
            header.account = Authentication.getAuthenticatedAccount();
            console.log(header.account);
        }

        function loginError(data){

        }

        function register(){
            Authentication.register(header.email, header.username, header.first_name, header.last_name, header.password, header.confirm_password).then(registerSuccess, registerError);

            function registerSuccess(){
                loginafterRegister(header.email, header.password);
            }
        }



        function registerError(data){
            console.error(data.data);
        }

    }


})();
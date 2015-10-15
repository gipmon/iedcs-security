(function (){
    'use strict';

    angular
        .module('webstore.authentication.controllers')
        .controller('HeaderController', HeaderController);

    HeaderController.$inject = ['Authentication', '$scope', '$rootScope'];

    function HeaderController(Authentication, $scope, $rootScope){
        var header = this;

        header.login = login;
        header.register = register;
        header.logout = logout;
        if(Authentication.isAuthenticated()) {
            header.account = Authentication.getAuthenticatedAccount();

        }

        activate();

        function activate(){
            $scope.loader = {
                loading: false
            };
            header.loginerror=false;
            header.registerError = false;

            if(Authentication.isAuthenticated()){
                header.account = Authentication.getAuthenticatedAccount();
                document.getElementById("demo").innerHTML ="Welcome " + header.account.first_name + " " + header.account.last_name +"!";

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

            activate();
            header.account = Authentication.getAuthenticatedAccount();
            console.log(header.account);
        }

        function loginError(data){
            header.loginerror = true;
        }

        function register(){
            Authentication.register(header.email, header.username, header.first_name, header.last_name, header.password, header.confirm_password).then(registerSuccess, registerError);

            function registerSuccess(){
                loginafterRegister(header.email, header.password);
            }
        }



        function registerError(data){
            var errors = "";
            for (var value in data.data.message) {
                errors += "&bull; " + (value.charAt(0).toUpperCase() + value.slice(1)).replace("_", " ") + ":<br/>"
                for (var error in data.data.message[value]){
                    errors += " &nbsp; "+ data.data.message[value][error] + '<br/>';
                }
            }
            if(typeof data.data.detail !== 'undefined'){
                errors += " &nbsp; "+ data.data.detail + '<br/>';
            }
            document.getElementById("registerError").innerHTML = errors;
            header.registerError = true;
        }

        function logout(){
            Authentication.logout();
            activate();
        }

    }


})();
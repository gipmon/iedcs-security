(function (){
    'use strict';

    angular
        .module('webstore.authentication.controllers')
        .controller('HeaderController', HeaderController);

    HeaderController.$inject = ['Authentication', '$scope', 'WebSite', '$location'];

    function HeaderController(Authentication, $scope, WebSite, $location){
        var header = this;
        var vm = this;

        header.login = login;
        header.register = register;
        header.logout = logout;
        activate();

        function activate(){
            $scope.loader = {
                loading: false
            };
            header.loginerror=false;
            header.registerError = false;

            if(Authentication.isAuthenticated()){
                console.log("entrei");
                header.account = Authentication.getAuthenticatedAccount();
                document.getElementById("demo").innerHTML ="Welcome " + header.account.first_name + " " + header.account.last_name +"!";
                header.logged = true;
                header.hide = false;
                vm.logged = true;
                vm.hide = false;

            }else{
                header.logged = false;
                header.hide = true;
                vm.logged = false;
                vm.hide = true;
            }
            WebSite.getAllBooks().then(getAllBooksSuccess, getAllBooksError);

            function getAllBooksSuccess(data){
                vm.books = data.data;
                for(var i=0; i <vm.books.length; i++){
                    if(vm.books[i].name.length >= 43){
                        vm.books[i].name = vm.books[i].name.substring(0, 40) + "...";
                    }
                }
                $scope.loader = {
                    loading: true
                };
            }

            function getAllBooksError(data){
                console.error(data.data);
                $scope.loader = {
                    loading: true
                };
            }


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
            header.account = Authentication.getAuthenticatedAccount();
            document.getElementById("demo").innerHTML ="Welcome " + header.account.first_name + " " + header.account.last_name +"!";

            $location.url('/home');
            header.logged = true;
            header.hide = false;
            vm.logged = true;
            vm.hide = false;        }

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
            Authentication.logout().then(logoutSuccessFn, logoutErrorFn);
        }
        function logoutSuccessFn() {
            Authentication.unauthenticate();
            $location.url('/');
            header.logged = false;
            header.hide = true;
            vm.logged = false;
            vm.hide = true;
        }
        function logoutErrorFn(){
            console.error("Logout Failure!");
        }

    }


})();
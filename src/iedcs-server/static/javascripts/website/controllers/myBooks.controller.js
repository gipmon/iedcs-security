(function (){
    'use strict';

    angular
        .module('webstore.website.controllers')
        .controller('MyBooksController', MyBooksController);

    MyBooksController.$inject = ['WebSite', '$scope', 'Authentication', '$location'];

    function MyBooksController(WebSite, $scope, Authentication, $location){
        var vm = this;

        activate();

        function activate(){
            $scope.loader = {
                loading: false
            };
            if(Authentication.isAuthenticated()){
                console.log("net");
                WebSite.getMyBooks().then(getBookSuccess, getBookError);

            }else{
                console.log("gee");

                $location.url("/");
            }

            function getBookSuccess(data){
                vm.books = data.data;
                console.log(vm.books);

                $scope.loader = {
                    loading: true
                };
            }

            function getBookError(data){
                console.error(data.data);
                $scope.loader = {
                    loading: true
                };
            }


        }

    }


})();
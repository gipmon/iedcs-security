(function (){
    'use strict';

    angular
        .module('webstore.website.controllers')
        .controller('WebSiteController', WebSiteController);

    WebSiteController.$inject = ['WebSite', '$scope', 'Authentication', '$location'];

    function WebSiteController(WebSite, $scope, Authentication, $location){
        var vm = this;

        activate();

        function activate(){
            $scope.loader = {
                loading: false
            };
            WebSite.getAllBooks().then(getAllBooksSuccess, getAllBooksError);

            function getAllBooksSuccess(data){
                vm.books = data.data;
                for(var i=0; i <vm.books.length; i++){
                    if(vm.books[i].name.length >= 43){
                        vm.books[i].name = vm.books[i].name.substring(0, 40) + "...";
                    }
                }
                if(Authentication.isAuthenticated()){
                    var account = Authentication.getAuthenticatedAccount();

                    vm.first_name = account.first_name;
                    vm.last_name = account.last_name;


                }else{

                    //$location.url('/');
                }
                $scope.loader = {
                    loading: true
                };
                console.log(vm.books);
            }

            function getAllBooksError(data){
                console.error(data.data);
                $scope.loader = {
                    loading: true
                };
            }

        }
    }


})();
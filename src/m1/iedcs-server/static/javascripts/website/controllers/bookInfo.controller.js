(function (){
    'use strict';

    angular
        .module('webstore.website.controllers')
        .controller('BookInfoController', BookInfoController);

    BookInfoController.$inject = ['WebSite', '$scope', 'Authentication', '$routeParams', '$location'];

    function BookInfoController(WebSite, $scope, Authentication, $routeParams, $location){
        var vm = this;

        vm.orderBook = orderBook;

        activate();

        function activate(){
            $scope.loader = {
                loading: false
            };

            vm.id = $routeParams.identifier;
            WebSite.getBook(vm.id).then(getBookSuccess, getBookError);

            function getBookSuccess(data){
                vm.book = data.data;
                console.log(vm.book);
                if(Authentication.isAuthenticated()){
                    vm.logged = true;
                    vm.hide = false;

                }else{
                    vm.logged = false;
                    vm.hide = true;
                }
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

        function orderBook(){
            WebSite.orderBook(vm.id).then(orderSuccess, orderError);

            function orderSuccess(){
                $location.url("/myBooks/");
            }

            function orderError(data){
                vm.buyError = true;
                document.getElementById("buyError").innerHTML = data.data.message;
            }
        }
    }


})();
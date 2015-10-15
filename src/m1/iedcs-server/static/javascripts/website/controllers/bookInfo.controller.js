(function (){
    'use strict';

    angular
        .module('webstore.website.controllers')
        .controller('BookInfoController', BookInfoController);

    BookInfoController.$inject = ['WebSite', '$scope', 'Authentication', '$location', '$routeParams'];

    function BookInfoController(WebSite, $scope, Authentication, $location, $routeParams){
        var vm = this;

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
    }


})();
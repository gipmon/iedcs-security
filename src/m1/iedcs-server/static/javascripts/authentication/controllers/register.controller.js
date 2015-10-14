(function (){
    'use strict';

    angular
        .module('webstore.authentication.controllers')
        .controller('RegisterController', RegisterController);

    RegisterController.$inject = ['$location'];

    function RegisterController($location){
        var vm = this;

        activate();

        function activate(){

            console.log("helloregister");


        }
    }


})();
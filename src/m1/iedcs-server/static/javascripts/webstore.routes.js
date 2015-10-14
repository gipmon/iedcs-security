(function(){
    'use strict';

    angular
        .module('webstore.routes')
        .config(config);

    config.$inject = ['$routeProvider'];

    function config($routeProvider){
        $routeProvider.when('/home/',{
            templateUrl: '/static/templates/index.html/'
        }).when('/' ,{
            controller: 'RegisterController',
            templateUrl: '/static/templates/index.html'
        }).otherwise('/');
    }


})();
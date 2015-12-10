(function(){
    'use strict';

    angular
        .module('webstore.routes')
        .config(config);

    config.$inject = ['$routeProvider'];

    function config($routeProvider){
        $routeProvider.when('/home/',{
            controller: 'WebSiteController',
            controllerAs: 'vm',
            templateUrl: '/static/templates/home.html'
        }).when('/myBooks/' ,{
            controller: 'MyBooksController',
            controllerAs: 'vm',
            templateUrl: '/static/templates/myBooks.html'
        }).when('/:identifier/' ,{
            controller: 'BookInfoController',
            controllerAs: 'vm',
            templateUrl: '/static/templates/bookInfo.html'
        }).when('/' ,{
            controller: 'HeaderController',
            controllerAs: 'vm',
            templateUrl: '/static/templates/index.html'
        }).otherwise('/');
    }


})();
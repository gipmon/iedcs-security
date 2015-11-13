(function (){
    'use strict';

    angular
        .module('webstore', [
            'webstore.config',
            'webstore.routes',
            'webstore.authentication',
            'webstore.website',
            'angularjs-crypto'
        ])
        .run(run);

    angular
        .module('webstore.config', []);

    angular
        .module('webstore.routes', ['ngRoute']);

    angular
        .module('angularjs-crypto', []);

    run.$inject = ['$http', '$rootScope'];

    function run($http, $rootScope){
        console.log("RUN");
        $http.defaults.xsrfHeaderName = 'X-CSRFToken';
        $http.defaults.xsrfCookieName = 'csrftoken';
        $rootScope.$on("$routeChangeSuccess", function(event, currentRoute, previousRoute) {
            $rootScope.title = currentRoute.title;
        });

    }

})();
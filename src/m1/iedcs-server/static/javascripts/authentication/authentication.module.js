(function (){
    'use strict';

    angular
        .module('webstore.authentication', [
            'webstore.authentication.controllers',
            'webstore.authentication.services'
        ]);

    angular
        .module('webstore.authentication.controllers', []);

    angular
        .module('webstore.authentication.services', ['ngCookies']);
})();
(function (){
    'use strict';

    angular
        .module('webstore.website', [
            'webstore.website.controllers',
            'webstore.website.services'
        ]);

    angular
        .module('webstore.website.controllers', []);

    angular
        .module('webstore.website.services', ['ngCookies']);
})();
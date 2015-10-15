(function () {
    'use strict';

    angular
        .module("webstore.website.services")
        .factory("WebSite", WebSite);

    WebSite.$inject = ["$http"];

    function WebSite($http){
        var WebSite = {
            getAllBooks: getAllBooks,
            getBook: getBook,
            orderBook: orderBook

        };

        return WebSite;

        function getAllBooks(){
            return $http.get("/api/v1/books/");
        }

        function getBook(identifier){
            return $http.get("/api/v1/books/" + identifier + "/");
        }

        function orderBook(identifier){
            return $http.post("/api/v1/user_books/", {
                book_identifier: identifier
            });
        }

    }
})();
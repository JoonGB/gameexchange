(function() {
    'use strict';
    angular
        .module('gameexchangeApp')
        .factory('Videojuego', Videojuego);

    Videojuego.$inject = ['$resource'];

    function Videojuego ($resource) {
        var resourceUrl =  'api/videojuegos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();

(function() {
    'use strict';
    angular
        .module('gameexchangeApp')
        .factory('Mensaje', Mensaje);

    Mensaje.$inject = ['$resource', 'DateUtils'];

    function Mensaje ($resource, DateUtils) {
        var resourceUrl =  'api/mensajes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.creado = DateUtils.convertDateTimeFromServer(data.creado);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();

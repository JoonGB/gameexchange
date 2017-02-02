(function() {
    'use strict';
    angular
        .module('gameexchangeApp')
        .factory('Venta', Venta);

    Venta.$inject = ['$resource', 'DateUtils'];

    function Venta ($resource, DateUtils) {
        var resourceUrl =  'api/ventas/:id';

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

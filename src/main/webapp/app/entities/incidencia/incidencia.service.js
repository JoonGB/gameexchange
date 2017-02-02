(function() {
    'use strict';
    angular
        .module('gameexchangeApp')
        .factory('Incidencia', Incidencia);

    Incidencia.$inject = ['$resource', 'DateUtils'];

    function Incidencia ($resource, DateUtils) {
        var resourceUrl =  'api/incidencias/:id';

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

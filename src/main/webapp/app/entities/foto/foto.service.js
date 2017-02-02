(function() {
    'use strict';
    angular
        .module('gameexchangeApp')
        .factory('Foto', Foto);

    Foto.$inject = ['$resource', 'DateUtils'];

    function Foto ($resource, DateUtils) {
        var resourceUrl =  'api/fotos/:id';

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

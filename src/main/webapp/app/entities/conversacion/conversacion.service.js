(function() {
    'use strict';
    angular
        .module('gameexchangeApp')
        .factory('Conversacion', Conversacion);

    Conversacion.$inject = ['$resource', 'DateUtils'];

    function Conversacion ($resource, DateUtils) {
        var resourceUrl =  'api/conversacions/:id';

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

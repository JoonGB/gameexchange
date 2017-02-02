(function() {
    'use strict';

    angular
        .module('gameexchangeApp')
        .controller('IncidenciaDetailController', IncidenciaDetailController);

    IncidenciaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Incidencia', 'User', 'Venta'];

    function IncidenciaDetailController($scope, $rootScope, $stateParams, previousState, entity, Incidencia, User, Venta) {
        var vm = this;

        vm.incidencia = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('gameexchangeApp:incidenciaUpdate', function(event, result) {
            vm.incidencia = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

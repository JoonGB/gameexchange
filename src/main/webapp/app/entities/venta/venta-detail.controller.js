(function() {
    'use strict';

    angular
        .module('gameexchangeApp')
        .controller('VentaDetailController', VentaDetailController);

    VentaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Venta', 'User', 'Producto', 'Incidencia'];

    function VentaDetailController($scope, $rootScope, $stateParams, previousState, entity, Venta, User, Producto, Incidencia) {
        var vm = this;

        vm.venta = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('gameexchangeApp:ventaUpdate', function(event, result) {
            vm.venta = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

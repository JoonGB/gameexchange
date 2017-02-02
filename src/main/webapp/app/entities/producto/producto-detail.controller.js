(function() {
    'use strict';

    angular
        .module('gameexchangeApp')
        .controller('ProductoDetailController', ProductoDetailController);

    ProductoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Producto', 'Videojuego', 'User', 'Foto', 'Venta'];

    function ProductoDetailController($scope, $rootScope, $stateParams, previousState, entity, Producto, Videojuego, User, Foto, Venta) {
        var vm = this;

        vm.producto = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('gameexchangeApp:productoUpdate', function(event, result) {
            vm.producto = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

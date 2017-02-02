(function() {
    'use strict';

    angular
        .module('gameexchangeApp')
        .controller('VideojuegoDetailController', VideojuegoDetailController);

    VideojuegoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Videojuego', 'Categoria', 'Producto'];

    function VideojuegoDetailController($scope, $rootScope, $stateParams, previousState, entity, Videojuego, Categoria, Producto) {
        var vm = this;

        vm.videojuego = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('gameexchangeApp:videojuegoUpdate', function(event, result) {
            vm.videojuego = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

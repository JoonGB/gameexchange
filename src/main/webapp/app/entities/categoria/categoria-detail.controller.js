(function() {
    'use strict';

    angular
        .module('gameexchangeApp')
        .controller('CategoriaDetailController', CategoriaDetailController);

    CategoriaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Categoria', 'Videojuego'];

    function CategoriaDetailController($scope, $rootScope, $stateParams, previousState, entity, Categoria, Videojuego) {
        var vm = this;

        vm.categoria = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('gameexchangeApp:categoriaUpdate', function(event, result) {
            vm.categoria = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

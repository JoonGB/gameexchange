(function() {
    'use strict';

    angular
        .module('gameexchangeApp')
        .controller('VideojuegoController', VideojuegoController);

    VideojuegoController.$inject = ['$scope', '$state', 'Videojuego'];

    function VideojuegoController ($scope, $state, Videojuego) {
        var vm = this;

        vm.videojuegos = [];

        loadAll();

        function loadAll() {
            Videojuego.query(function(result) {
                vm.videojuegos = result;
                vm.searchQuery = null;
            });
        }
    }
})();

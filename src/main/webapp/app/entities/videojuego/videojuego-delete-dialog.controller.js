(function() {
    'use strict';

    angular
        .module('gameexchangeApp')
        .controller('VideojuegoDeleteController',VideojuegoDeleteController);

    VideojuegoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Videojuego'];

    function VideojuegoDeleteController($uibModalInstance, entity, Videojuego) {
        var vm = this;

        vm.videojuego = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Videojuego.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

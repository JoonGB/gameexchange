(function() {
    'use strict';

    angular
        .module('gameexchangeApp')
        .controller('ConversacionDeleteController',ConversacionDeleteController);

    ConversacionDeleteController.$inject = ['$uibModalInstance', 'entity', 'Conversacion'];

    function ConversacionDeleteController($uibModalInstance, entity, Conversacion) {
        var vm = this;

        vm.conversacion = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Conversacion.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

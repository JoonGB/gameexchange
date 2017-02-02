(function() {
    'use strict';

    angular
        .module('gameexchangeApp')
        .controller('IncidenciaDeleteController',IncidenciaDeleteController);

    IncidenciaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Incidencia'];

    function IncidenciaDeleteController($uibModalInstance, entity, Incidencia) {
        var vm = this;

        vm.incidencia = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Incidencia.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

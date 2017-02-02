(function() {
    'use strict';

    angular
        .module('gameexchangeApp')
        .controller('ProductoDeleteController',ProductoDeleteController);

    ProductoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Producto'];

    function ProductoDeleteController($uibModalInstance, entity, Producto) {
        var vm = this;

        vm.producto = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Producto.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

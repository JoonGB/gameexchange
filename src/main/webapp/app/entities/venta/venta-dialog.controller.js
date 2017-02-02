(function() {
    'use strict';

    angular
        .module('gameexchangeApp')
        .controller('VentaDialogController', VentaDialogController);

    VentaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Venta', 'User', 'Producto', 'Incidencia'];

    function VentaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Venta, User, Producto, Incidencia) {
        var vm = this;

        vm.venta = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.users = User.query();
        vm.productos = Producto.query();
        vm.incidencias = Incidencia.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.venta.id !== null) {
                Venta.update(vm.venta, onSaveSuccess, onSaveError);
            } else {
                Venta.save(vm.venta, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('gameexchangeApp:ventaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.creado = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();

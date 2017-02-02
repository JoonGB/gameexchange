(function() {
    'use strict';

    angular
        .module('gameexchangeApp')
        .controller('IncidenciaDialogController', IncidenciaDialogController);

    IncidenciaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Incidencia', 'User', 'Venta'];

    function IncidenciaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Incidencia, User, Venta) {
        var vm = this;

        vm.incidencia = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.users = User.query();
        vm.ventas = Venta.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.incidencia.id !== null) {
                Incidencia.update(vm.incidencia, onSaveSuccess, onSaveError);
            } else {
                Incidencia.save(vm.incidencia, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('gameexchangeApp:incidenciaUpdate', result);
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

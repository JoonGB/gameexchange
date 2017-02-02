(function() {
    'use strict';

    angular
        .module('gameexchangeApp')
        .controller('MensajeDialogController', MensajeDialogController);

    MensajeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Mensaje', 'Conversacion', 'User'];

    function MensajeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Mensaje, Conversacion, User) {
        var vm = this;

        vm.mensaje = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.conversacions = Conversacion.query();
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.mensaje.id !== null) {
                Mensaje.update(vm.mensaje, onSaveSuccess, onSaveError);
            } else {
                Mensaje.save(vm.mensaje, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('gameexchangeApp:mensajeUpdate', result);
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

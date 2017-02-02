(function() {
    'use strict';

    angular
        .module('gameexchangeApp')
        .controller('ConversacionDialogController', ConversacionDialogController);

    ConversacionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Conversacion', 'Mensaje'];

    function ConversacionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Conversacion, Mensaje) {
        var vm = this;

        vm.conversacion = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.mensajes = Mensaje.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.conversacion.id !== null) {
                Conversacion.update(vm.conversacion, onSaveSuccess, onSaveError);
            } else {
                Conversacion.save(vm.conversacion, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('gameexchangeApp:conversacionUpdate', result);
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

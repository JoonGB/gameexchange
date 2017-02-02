(function() {
    'use strict';

    angular
        .module('gameexchangeApp')
        .controller('MensajeDetailController', MensajeDetailController);

    MensajeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Mensaje', 'Conversacion', 'User'];

    function MensajeDetailController($scope, $rootScope, $stateParams, previousState, entity, Mensaje, Conversacion, User) {
        var vm = this;

        vm.mensaje = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('gameexchangeApp:mensajeUpdate', function(event, result) {
            vm.mensaje = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

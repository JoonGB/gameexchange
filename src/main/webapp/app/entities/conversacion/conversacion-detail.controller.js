(function() {
    'use strict';

    angular
        .module('gameexchangeApp')
        .controller('ConversacionDetailController', ConversacionDetailController);

    ConversacionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Conversacion', 'Mensaje'];

    function ConversacionDetailController($scope, $rootScope, $stateParams, previousState, entity, Conversacion, Mensaje) {
        var vm = this;

        vm.conversacion = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('gameexchangeApp:conversacionUpdate', function(event, result) {
            vm.conversacion = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

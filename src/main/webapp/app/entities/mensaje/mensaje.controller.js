(function() {
    'use strict';

    angular
        .module('gameexchangeApp')
        .controller('MensajeController', MensajeController);

    MensajeController.$inject = ['$scope', '$state', 'Mensaje'];

    function MensajeController ($scope, $state, Mensaje) {
        var vm = this;

        vm.mensajes = [];

        loadAll();

        function loadAll() {
            Mensaje.query(function(result) {
                vm.mensajes = result;
                vm.searchQuery = null;
            });
        }
    }
})();

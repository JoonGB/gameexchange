(function() {
    'use strict';

    angular
        .module('gameexchangeApp')
        .controller('ConversacionController', ConversacionController);

    ConversacionController.$inject = ['$scope', '$state', 'Conversacion'];

    function ConversacionController ($scope, $state, Conversacion) {
        var vm = this;

        vm.conversacions = [];

        loadAll();

        function loadAll() {
            Conversacion.query(function(result) {
                vm.conversacions = result;
                vm.searchQuery = null;
            });
        }
    }
})();

(function() {
    'use strict';

    angular
        .module('gameexchangeApp')
        .controller('IncidenciaController', IncidenciaController);

    IncidenciaController.$inject = ['$scope', '$state', 'Incidencia'];

    function IncidenciaController ($scope, $state, Incidencia) {
        var vm = this;

        vm.incidencias = [];

        loadAll();

        function loadAll() {
            Incidencia.query(function(result) {
                vm.incidencias = result;
                vm.searchQuery = null;
            });
        }
    }
})();

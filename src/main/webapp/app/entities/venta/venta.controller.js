(function() {
    'use strict';

    angular
        .module('gameexchangeApp')
        .controller('VentaController', VentaController);

    VentaController.$inject = ['$scope', '$state', 'Venta'];

    function VentaController ($scope, $state, Venta) {
        var vm = this;

        vm.ventas = [];

        loadAll();

        function loadAll() {
            Venta.query(function(result) {
                vm.ventas = result;
                vm.searchQuery = null;
            });
        }
    }
})();

(function() {
    'use strict';

    angular
        .module('gameexchangeApp')
        .controller('ProductoController', ProductoController);

    ProductoController.$inject = ['$scope', '$state', 'Producto'];

    function ProductoController ($scope, $state, Producto) {
        var vm = this;

        vm.productos = [];

        loadAll();

        function loadAll() {
            Producto.query(function(result) {
                vm.productos = result;
                vm.searchQuery = null;
            });
        }
    }
})();

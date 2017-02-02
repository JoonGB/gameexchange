(function() {
    'use strict';

    angular
        .module('gameexchangeApp')
        .controller('CategoriaController', CategoriaController);

    CategoriaController.$inject = ['$scope', '$state', 'Categoria'];

    function CategoriaController ($scope, $state, Categoria) {
        var vm = this;

        vm.categorias = [];

        loadAll();

        function loadAll() {
            Categoria.query(function(result) {
                vm.categorias = result;
                vm.searchQuery = null;
            });
        }
    }
})();

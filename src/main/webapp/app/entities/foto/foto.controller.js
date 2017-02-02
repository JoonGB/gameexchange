(function() {
    'use strict';

    angular
        .module('gameexchangeApp')
        .controller('FotoController', FotoController);

    FotoController.$inject = ['$scope', '$state', 'DataUtils', 'Foto'];

    function FotoController ($scope, $state, DataUtils, Foto) {
        var vm = this;

        vm.fotos = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();

        function loadAll() {
            Foto.query(function(result) {
                vm.fotos = result;
                vm.searchQuery = null;
            });
        }
    }
})();

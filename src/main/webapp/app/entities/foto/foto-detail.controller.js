(function() {
    'use strict';

    angular
        .module('gameexchangeApp')
        .controller('FotoDetailController', FotoDetailController);

    FotoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Foto', 'Producto'];

    function FotoDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Foto, Producto) {
        var vm = this;

        vm.foto = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('gameexchangeApp:fotoUpdate', function(event, result) {
            vm.foto = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

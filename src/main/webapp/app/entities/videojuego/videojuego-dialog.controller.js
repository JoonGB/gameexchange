(function() {
    'use strict';

    angular
        .module('gameexchangeApp')
        .controller('VideojuegoDialogController', VideojuegoDialogController);

    VideojuegoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Videojuego', 'Categoria', 'Producto'];

    function VideojuegoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Videojuego, Categoria, Producto) {
        var vm = this;

        vm.videojuego = entity;
        vm.clear = clear;
        vm.save = save;
        vm.categorias = Categoria.query();
        vm.productos = Producto.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.videojuego.id !== null) {
                Videojuego.update(vm.videojuego, onSaveSuccess, onSaveError);
            } else {
                Videojuego.save(vm.videojuego, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('gameexchangeApp:videojuegoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();

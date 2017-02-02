(function() {
    'use strict';

    angular
        .module('gameexchangeApp')
        .controller('UserExtController', UserExtController);

    UserExtController.$inject = ['$scope', '$state', 'DataUtils', 'UserExt'];

    function UserExtController ($scope, $state, DataUtils, UserExt) {
        var vm = this;

        vm.userExts = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();

        function loadAll() {
            UserExt.query(function(result) {
                vm.userExts = result;
                vm.searchQuery = null;
            });
        }
    }
})();

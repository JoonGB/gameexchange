(function() {
    'use strict';

    angular
        .module('gameexchangeApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('conversacion', {
            parent: 'entity',
            url: '/conversacion',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'gameexchangeApp.conversacion.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/conversacion/conversacions.html',
                    controller: 'ConversacionController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('conversacion');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('conversacion-detail', {
            parent: 'entity',
            url: '/conversacion/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'gameexchangeApp.conversacion.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/conversacion/conversacion-detail.html',
                    controller: 'ConversacionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('conversacion');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Conversacion', function($stateParams, Conversacion) {
                    return Conversacion.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'conversacion',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('conversacion-detail.edit', {
            parent: 'conversacion-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/conversacion/conversacion-dialog.html',
                    controller: 'ConversacionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Conversacion', function(Conversacion) {
                            return Conversacion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('conversacion.new', {
            parent: 'conversacion',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/conversacion/conversacion-dialog.html',
                    controller: 'ConversacionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                creado: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('conversacion', null, { reload: 'conversacion' });
                }, function() {
                    $state.go('conversacion');
                });
            }]
        })
        .state('conversacion.edit', {
            parent: 'conversacion',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/conversacion/conversacion-dialog.html',
                    controller: 'ConversacionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Conversacion', function(Conversacion) {
                            return Conversacion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('conversacion', null, { reload: 'conversacion' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('conversacion.delete', {
            parent: 'conversacion',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/conversacion/conversacion-delete-dialog.html',
                    controller: 'ConversacionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Conversacion', function(Conversacion) {
                            return Conversacion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('conversacion', null, { reload: 'conversacion' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

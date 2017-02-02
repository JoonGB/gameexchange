(function() {
    'use strict';

    angular
        .module('gameexchangeApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('incidencia', {
            parent: 'entity',
            url: '/incidencia',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'gameexchangeApp.incidencia.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/incidencia/incidencias.html',
                    controller: 'IncidenciaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('incidencia');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('incidencia-detail', {
            parent: 'entity',
            url: '/incidencia/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'gameexchangeApp.incidencia.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/incidencia/incidencia-detail.html',
                    controller: 'IncidenciaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('incidencia');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Incidencia', function($stateParams, Incidencia) {
                    return Incidencia.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'incidencia',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('incidencia-detail.edit', {
            parent: 'incidencia-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/incidencia/incidencia-dialog.html',
                    controller: 'IncidenciaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Incidencia', function(Incidencia) {
                            return Incidencia.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('incidencia.new', {
            parent: 'incidencia',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/incidencia/incidencia-dialog.html',
                    controller: 'IncidenciaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                creado: null,
                                descripcion: null,
                                titulo: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('incidencia', null, { reload: 'incidencia' });
                }, function() {
                    $state.go('incidencia');
                });
            }]
        })
        .state('incidencia.edit', {
            parent: 'incidencia',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/incidencia/incidencia-dialog.html',
                    controller: 'IncidenciaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Incidencia', function(Incidencia) {
                            return Incidencia.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('incidencia', null, { reload: 'incidencia' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('incidencia.delete', {
            parent: 'incidencia',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/incidencia/incidencia-delete-dialog.html',
                    controller: 'IncidenciaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Incidencia', function(Incidencia) {
                            return Incidencia.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('incidencia', null, { reload: 'incidencia' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

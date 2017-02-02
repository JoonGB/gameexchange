(function() {
    'use strict';

    angular
        .module('gameexchangeApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('videojuego', {
            parent: 'entity',
            url: '/videojuego',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'gameexchangeApp.videojuego.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/videojuego/videojuegos.html',
                    controller: 'VideojuegoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('videojuego');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('videojuego-detail', {
            parent: 'entity',
            url: '/videojuego/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'gameexchangeApp.videojuego.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/videojuego/videojuego-detail.html',
                    controller: 'VideojuegoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('videojuego');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Videojuego', function($stateParams, Videojuego) {
                    return Videojuego.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'videojuego',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('videojuego-detail.edit', {
            parent: 'videojuego-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/videojuego/videojuego-dialog.html',
                    controller: 'VideojuegoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Videojuego', function(Videojuego) {
                            return Videojuego.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('videojuego.new', {
            parent: 'videojuego',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/videojuego/videojuego-dialog.html',
                    controller: 'VideojuegoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('videojuego', null, { reload: 'videojuego' });
                }, function() {
                    $state.go('videojuego');
                });
            }]
        })
        .state('videojuego.edit', {
            parent: 'videojuego',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/videojuego/videojuego-dialog.html',
                    controller: 'VideojuegoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Videojuego', function(Videojuego) {
                            return Videojuego.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('videojuego', null, { reload: 'videojuego' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('videojuego.delete', {
            parent: 'videojuego',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/videojuego/videojuego-delete-dialog.html',
                    controller: 'VideojuegoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Videojuego', function(Videojuego) {
                            return Videojuego.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('videojuego', null, { reload: 'videojuego' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

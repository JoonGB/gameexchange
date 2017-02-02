'use strict';

describe('Controller Tests', function() {

    describe('Producto Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockProducto, MockVideojuego, MockUser, MockFoto, MockVenta;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockProducto = jasmine.createSpy('MockProducto');
            MockVideojuego = jasmine.createSpy('MockVideojuego');
            MockUser = jasmine.createSpy('MockUser');
            MockFoto = jasmine.createSpy('MockFoto');
            MockVenta = jasmine.createSpy('MockVenta');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Producto': MockProducto,
                'Videojuego': MockVideojuego,
                'User': MockUser,
                'Foto': MockFoto,
                'Venta': MockVenta
            };
            createController = function() {
                $injector.get('$controller')("ProductoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'gameexchangeApp:productoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});

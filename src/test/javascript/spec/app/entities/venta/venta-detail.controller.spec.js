'use strict';

describe('Controller Tests', function() {

    describe('Venta Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockVenta, MockUser, MockProducto, MockIncidencia;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockVenta = jasmine.createSpy('MockVenta');
            MockUser = jasmine.createSpy('MockUser');
            MockProducto = jasmine.createSpy('MockProducto');
            MockIncidencia = jasmine.createSpy('MockIncidencia');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Venta': MockVenta,
                'User': MockUser,
                'Producto': MockProducto,
                'Incidencia': MockIncidencia
            };
            createController = function() {
                $injector.get('$controller')("VentaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'gameexchangeApp:ventaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});

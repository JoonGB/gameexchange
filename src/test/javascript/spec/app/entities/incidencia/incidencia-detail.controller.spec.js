'use strict';

describe('Controller Tests', function() {

    describe('Incidencia Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockIncidencia, MockUser, MockVenta;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockIncidencia = jasmine.createSpy('MockIncidencia');
            MockUser = jasmine.createSpy('MockUser');
            MockVenta = jasmine.createSpy('MockVenta');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Incidencia': MockIncidencia,
                'User': MockUser,
                'Venta': MockVenta
            };
            createController = function() {
                $injector.get('$controller')("IncidenciaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'gameexchangeApp:incidenciaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});

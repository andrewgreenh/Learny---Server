angular.module('learny').directive(
        'globalmodal',
        function() {
            return {
                restrict : 'E',
                templateUrl : 'partials/globalModal/globalModal.html',
                scope : {
                    text : '=',
                    callback : '&'
                },
                controller : [
                        '$scope',
                        '$state',
                        'serverCommunicator',
                        function($scope, $state, serverCommunicator) {
                            var modal = $('#globalModal');
                            
                            $scope.cancel = function() {
                            };
                            
                            $scope.go = function() {
                                $("#globalModal").one('hidden.bs.modal', function(e) {
                                    $scope.callback();
                                }).modal("hide");
                            }
                        } ],

            }
        });
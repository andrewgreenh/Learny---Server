angular.module('learny').controller('requestPasswordController',
        [ '$scope', '$state', 'serverCommunicator',

        function($scope, $state, serverCommunicator) {

           
            $scope.submit = function() {
                serverCommunicator.requestPasswordAsync($scope.email).then(function() {
                    $scope.success = true;
                    $scope.error = false;
                }, function() {
                    $scope.success = false;
                    $scope.error = true;
                });
            }

           

        } ]);
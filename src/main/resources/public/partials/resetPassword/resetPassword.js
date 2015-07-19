angular.module('learny').controller(
        'resetPasswordController',
        [
                '$scope',
                '$state',
                'serverCommunicator',
                'token',
                function($scope, $state, serverCommunicator, token) {

                    $scope.newPassword = "";
                    $scope.confirmPassword = "";
                    
                    $scope.submit = function() {
                        serverCommunicator.resetPasswordAsync(token, $scope.newPassword).then(function() {
                            $scope.success = true;
                            $scope.error = false;
                        }, function() {
                            $scope.success = false;
                            $scope.error = true;
                        });
                    }
                    
                    $scope.$watch('newPassword', passwordsAreMatching);
                    $scope.$watch('confirmPassword', passwordsAreMatching);
                    
                    function passwordsAreMatching(newVal) {
                        if($scope.newPassword == $scope.confirmPassword || newVal == undefined) {
                            $scope.passwordMismatch = false;
                        } else {
                            $scope.passwordMismatch = true;
                        }
                    }


                } ]);
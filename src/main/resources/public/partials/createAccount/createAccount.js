angular.module('learny').controller(
        'createAccountController',
        [
                '$scope',
                'serverCommunicator',
                function($scope, serverCommunicator) {
                    $scope.create = function() {
                        serverCommunicator.createAccountAsync($scope.user).then(function(){
                            $scope.created = true;
                            $scope.error = false;
                        }, function() {
                            $scope.error = true;
                            $scope.created = false;
                        });
                    }

                } ]);
angular.module('learny').controller(
        'editProfileController',
        [
                '$scope',
                '$state',
                'serverCommunicator',
                function($scope, $state, serverCommunicator) {

                    $scope.newUser = angular.copy($scope.currentUser);
                    
                    $scope.save = function() {
                        serverCommunicator
                                .updateProfileAsync($scope.currentUser.id, $scope.newUser).then(
                                        function(data) {
                                            $scope.setUser(data.data);
                                            $state.go('app.profile', {}, {
                                                reload : true
                                            });
                                        }, function() {
                                            console.log('Request fehlgeschlagen');
                                        });
                    }

                    $scope.cancel = function() {
                        $state.go('app.profile');
                    }

                } ]);
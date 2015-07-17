angular.module('learny').controller(
        'editProfileController',
        [
                '$scope',
                '$state',
                'serverCommunicator',
                function($scope, $state, serverCommunicator) {

                    $scope.newUser = angular.copy($scope.currentUser);
                    $scope.newPassword = "";
                    $scope.confirmPassword = "";
                    $scope.errorMsg = "";
                    $scope.showError = false;
                    
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
                    
                    $scope.savePasswords = function() {
                        $scope.showError = false;
                        serverCommunicator
                        .updatePasswordAsync($scope.oldPassword, $scope.newPassword).then(
                                function(data) {
                                    $state.go('app.profile', {}, {
                                        reload : true
                                    });
                                }, function(e) {
                                    if(e.status == 403) {
                                        $scope.errorMsg = "Das eingegebene Passwort war falsch";
                                    }
                                    $scope.showError = true;
                                    window.scrollTo(0, 0);
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

                    $scope.cancel = function() {
                        $state.go('app.profile');
                    }

                } ]);
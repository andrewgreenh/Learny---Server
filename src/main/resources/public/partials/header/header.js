angular.module('learny').directive(
        'learnyheader',
        function() {
            return {
                restrict : 'E',
                templateUrl : 'partials/header/header.tpl.html',
                scope : {
                    loggedin : '=',
                    welcomepage : '='
                },
                controller : [
                        '$scope',
                        '$state',
                        'serverCommunicator',
                        function($scope, $state, serverCommunicator) {
                            $scope.logout = function() {
                                serverCommunicator.logoutAsync().success(
                                        function(data, status, headers, config) {
                                            console.log('Successfully logged out');
                                            $state.go('welcome');
                                        }).error(function(data, status, headers, config) {
                                    console.log('Logout failed');
                                });

                            }

                            $scope.showlogin = false;
                            $scope.toggleLogin = function() {
                                serverCommunicator.isLoggedInAsync().success(function() {
                                    $state.go('app.home');
                                })
                                .error(function(){
                                    $scope.showlogin = !$scope.showlogin;                                    
                                });
                            }

                        } ],

            }
        });

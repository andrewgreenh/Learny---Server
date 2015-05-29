angular.module('learny').directive(
        'learnyloginform',
        function() {
            return {
                restrict : 'E',
                templateUrl : 'partials/login/loginForm.html',
                scope : {},
                controller : [
                        '$scope',
                        '$state',
                        'serverCommunicator',
                        function($scope, $state, serverCommunicator) {
                            
                            $scope.login = function() {
                                serverCommunicator.loginAsync($scope.username, $scope.password)
                                        .success(function(data, status, headers, config) {
                                            console.log('Successfully logged in');
                                            $state.go('app.home');
                                        }).error(function(data, status, headers, config) {
                                            console.log('Login failed');
                                        });

                            };
                        } ],

            }
        });
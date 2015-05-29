angular.module('learny').directive(
        'learnyheader',
        function() {
            return {
                restrict : 'E',
                templateUrl : 'partials/header/header.tpl.html',
                scope : {
                    loggedin : '='
                },
                controller : [
                        '$scope',
                        '$state',
                        'serverCommunicator',
                        function($scope, $state, serverCommunicator) {
                            console.log($scope.loggedin);
                            $scope.logout = function() {
                                serverCommunicator.logoutAsync().success(
                                        function(data, status, headers, config) {
                                            console.log('Successfully logged out');
                                            $state.go('login', {}, {
                                                reload : true
                                            });
                                        }).error(function(data, status, headers, config) {
                                    console.log('Logout failed');
                                });

                            }

                        } ],

            }
        });
angular.module('learny')
        .directive(
                'accountfinder',
                function() {
                    return {
                        restrict : 'E',
                        templateUrl : 'partials/accountFinder/accountFinder.tpl.html',
                        scope : {
                            callback : '&',
                        },
                        controller : [
                                '$scope',
                                '$state',
                                '$timeout',
                                'serverCommunicator',
                                function($scope, $state, $timeout, serverCommunicator) {

                                    $scope.users = [];

                                    $scope.find = function() {
                                        serverCommunicator.findDozentenAsync($scope.searchString)
                                                .then(function(data) {
                                                    $scope.users = data.data;
                                                });
                                    }

                                    $('accountfinder input').focusout(function() {
                                        $scope.users = [];
                                        $scope.active = -1;
                                        $scope.searchString = "";
                                        $scope.$apply();
                                    });

                                    $scope.handleKeyDownOnInput = function(event) {
                                        if (event.keyCode == 40
                                                && $scope.active < $scope.users.length - 1) {
                                            $scope.active++;
                                            event.preventDefault();
                                        } else if (event.keyCode == 38 && $scope.active > 0) {
                                            $scope.active--;
                                            event.preventDefault();
                                        } else if (event.keyCode == 13 && $scope.active != -1) {
                                            $scope.submit($scope.users[$scope.active]);
                                        }
                                    }

                                    var timeout = null;
                                    $scope.$watch('searchString', function(newVal, oldVal) {
                                        if (timeout == null) {
                                            timeout = $timeout(startSearch, 500, true, "test");
                                        } else {
                                            $timeout.cancel(timeout);
                                            timeout = $timeout(startSearch, 500, true, "test");
                                        }

                                    });

                                    $scope.submit = function(user) {
                                        $scope.callback({
                                            user : user
                                        });
                                    }

                                    function startSearch() {
                                        if ($scope.searchString.length > 3) {
                                            $scope.find();
                                        } else {
                                            $scope.users = [];
                                            $scope.active = -1;
                                        }
                                    }

                                    $scope.active = -1;
                                    $scope.isActive = function(index) {
                                        return $scope.active == index;
                                    };
                                } ],
                    }
                });

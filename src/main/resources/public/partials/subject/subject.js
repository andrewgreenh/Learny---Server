angular.module('learny').controller(
        'subjectController',
        [
                '$scope',
                '$state',
                'serverCommunicator',
                'tests',
                'subject',
                function($scope, $state, serverCommunicator, tests, subject) {

                    $scope.tests = tests.value.data;
                    $scope.subject = subject.value.data;

                    if ($scope.currentUser.isGlobalAdmin()
                            || $scope.currentUser.isAdminOf($scope.subject.id)) {
                        $scope.userIsAdmin = true;
                    } else {
                        $scope.userIsAdmin = false;
                    }

                    if ($scope.currentUser.isEnroledIn($scope.subject.id)) {
                        $scope.userIsEnroled = true;
                    } else {
                        $scope.userIsEnroled = false;
                    }

                    if (!$scope.userIsAdmin && !$scope.userIsEnroled) {
                        $scope.userIsNew = true;
                    } else {
                        $scope.userIsNew = false;
                    }

                    $scope.joinSubject = function() {
                        serverCommunicator.joinSubjectAsync($scope.subject).then(function(data) {
                            $state.go($state.current, {}, {
                                reload : true
                            });
                        }, function() {
                            console.log("Eintragen fehlgeschlagen");
                        });
                    }

                    $scope.addResponsible = function() {
                        serverCommunicator.addUserAsAdministratorToSubjectAsync($scope.accountName,
                                $scope.subject.id).then(function(data) {
                            $state.go($state.current, {}, {
                                reload : true
                            });
                        }, function(data) {
                            $state.go($state.current, {}, {
                                reload : true
                            });
                        })
                    }

                    $scope.removeFromResponsibles = function(person) {
                        serverCommunicator.removeAdministratorFromSubject(person.id, $scope.subject.id).then(
                                function(data) {
                                    $state.go($state.current, {}, {
                                        reload : true
                                    });
                                }, function(data) {
                                });
                    }

                } ]);
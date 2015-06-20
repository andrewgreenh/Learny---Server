angular.module('learny').controller(
        'createTestController',
        [
                '$scope',
                '$state',
                'serverCommunicator',
                'subject',
                function($scope, $state, serverCommunicator, subject) {
                    $scope.subject = subject.data;
                    $scope.test = {};
                    $scope.test.questions = [];

                    $scope.addQuestion = function() {
                        $scope.test.questions.push({
                            answers : []
                        });
                    };

                    $scope.addAnswer = function(question) {
                        question.answers.push({
                            correct : false
                        });
                    };

                    $scope.saveTest = function() {
                        serverCommunicator.addTestToSubject($scope.test, $scope.subject.id).then(
                                function() {
                                    $state.go('app.subject', {id: $scope.subject.id}, {
                                        reload : true
                                    });
                                }, function() {
                                });
                    }
                } ]);
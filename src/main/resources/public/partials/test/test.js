angular.module('learny').controller(
        'testController',
        [
                '$scope',
                '$state',
                'serverCommunicator',
                'test',
                'questions',
                function($scope, $state, serverCommunicator, test, questions) {
                    $scope.test = test.data;
                    $scope.test.questions = questions.data;
                    $scope.submit = function() {
                        serverCommunicator.turnInAnswersToTestAsync(
                                getAllCheckedAnswers($scope.test), $scope.test.id).then(
                                function(data) {
                                    $state.go('app.lastResult', {id: $scope.test.id});
                                }, function() {
                                    console.log('Test konnte nicht eingereicht werden.');
                                });
                    }

                    function getAllCheckedAnswers(test) {
                        checkedAnswers = [];
                        test.questions.forEach(function(question) {
                            question.answers.forEach(function(answer) {
                                if (answer.checked) {
                                    checkedAnswers.push(answer);
                                }
                            });
                        });
                        return checkedAnswers;
                    }
                } ]);
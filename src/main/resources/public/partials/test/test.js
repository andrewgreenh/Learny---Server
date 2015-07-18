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
                        serverCommunicator.turnInTestAsync(generatePushableTest($scope.test)).then(
                                function(data) {
                                    $state.go('app.lastResult', {
                                        id : $scope.test.id
                                    });
                                }, function() {
                                    console.log('Test konnte nicht eingereicht werden.');
                                });
                    }

                    function generatePushableTest(test) {
                        return {
                            id : test.id,
                            questions : test.questions.map(generatePushableQuestion),
                        }
                    }

                    function generatePushableQuestion(question) {
                        return {
                            id : question.id,
                            answers : question.answers.map(generatePushableAnswer)
                        }
                    }

                    function generatePushableAnswer(answer) {
                        return {
                            id : answer.id,
                            correct : (answer.checked === undefined) ? false : answer.checked
                        };
                    }
                } ]);
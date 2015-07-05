angular.module('learny').controller(
        'lastResultController',
        [ '$scope', '$state', 'serverCommunicator', 'test', 'questions', 'result',
                function($scope, $state, serverCommunicator, test, questions, result) {
                    $scope.checkedAnswers = result.data.checkedAnswers;
                    $scope.test = test.data;
                    $scope.test.questions = questions.data;
                    $scope.errorCounter = 0;

                    calculateErrors();

                    function calculateErrors() {
                        $scope.test.questions.forEach(function(question) {
                            question.answers.forEach(function(answer) {
                                if (isInCheckedAnswers(answer.id)) {
                                    answer.checked = true;
                                    console.log("lol");
                                }
                                if (wasAnswerRight(answer)) {
                                    answer.answeredCorrectly = true;
                                } else {
                                    answer.answeredCorrectly = false;
                                    $scope.errorCounter++;
                                }
                            })
                        });
                    }

                    function wasAnswerRight(answer) {
                        if (answer.correct && isInCheckedAnswers(answer.id)) {
                            return true;
                        } else if (!answer.correct && !isInCheckedAnswers(answer.id)) {
                            return true;
                        } else {
                            return false;
                        }

                    }

                    function isInCheckedAnswers(answerId) {
                        var result = false;
                        $scope.checkedAnswers.forEach(function(answer) {
                            if (answer.id == answerId) {
                                result = true;
                            }
                        });
                        return result;
                    }

                } ]);
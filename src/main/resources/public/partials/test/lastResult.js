angular.module('learny').controller(
        'lastResultController',
        [ '$scope', '$state', 'serverCommunicator', 'test', 'questions', 'result',
                function($scope, $state, serverCommunicator, test, questions, result) {
                    $scope.checkedAnswers = result.data.checkedAnswers;
                    $scope.uncheckedAnswers = result.data.uncheckedAnswers;
                    $scope.test = test.data;
                    $scope.test.questions = questions.data;
                    $scope.score = result.data.score;
                    calculateErrors();

                    
                    function calculateErrors() {
                        $scope.test.questions.forEach(function(question) {
                            for(var i = 0; i < question.answers.length; i++) {
                                question.answers[i] = findAnswerWithCorrectValue(question.answers[i]);
                                if(isAnswerCorrect(question.answers[i])) {
                                    question.answers[i].answeredCorrectly = true;
                                } else {
                                    question.answers[i].answeredCorrectly = false;
                                }
                            }
                        });
                    }
                    
                    function findAnswerWithCorrectValue(answer) {
                        var result = findInCheckedAnswers(answer);
                        if(result !== null) {
                            result.checked = true;
                        } else {
                            result = findInUncheckedAnswers(answer);
                            result.checked = false;
                        }
                        return result;
                    }
                    
                    function isAnswerCorrect(answer) {
                        if(answer.checked && answer.correct) {
                            return true;
                        } else if(!answer.checked && !answer.correct) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                    
                    function findInCheckedAnswers(answer) {
                        var result = null;
                        $scope.checkedAnswers.forEach(function(checkedAnswer) {
                           if(checkedAnswer.id == answer.id) {
                               result = checkedAnswer;
                           } 
                        });
                        return result;
                    }
                    
                    function findInUncheckedAnswers(answer) {
                        var result = null;
                        $scope.uncheckedAnswers.forEach(function(uncheckedAnswer) {
                           if(uncheckedAnswer.id == answer.id) {
                               result = uncheckedAnswer;
                           } 
                        });
                        return result;
                    }
                } ]);
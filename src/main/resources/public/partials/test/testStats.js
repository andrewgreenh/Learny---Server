angular.module('learny').controller(
        'testStatsController',
        [
                '$scope',
                '$state',
                'serverCommunicator',
                'test',
                'questions',
                function($scope, $state, serverCommunicator, test, questions) {
                    $scope.test = test.data;
                    $scope.test.questions = questions.data;

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
                    console.log($scope.test);
                } ]);
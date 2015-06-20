angular.module('learny').controller(
        'testController',
        [ '$scope', '$state', 'serverCommunicator', 'test', 'questions',
                function($scope, $state, serverCommunicator, test, questions) {
                    console.log(questions);
                    $scope.test = test.data;
                    $scope.test.questions = questions.data;
                    $scope.submit = function() {
                        console.log($scope.test);
                    }
                } ]);
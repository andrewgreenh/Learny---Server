angular.module('learny').controller(
        'testController',
        [ '$scope', '$state', 'serverCommunicator', 'test', 'questions',
                function($scope, $state, serverCommunicator, test, questions) {
                    console.log(test);
                    console.log(questions);
                } ]);
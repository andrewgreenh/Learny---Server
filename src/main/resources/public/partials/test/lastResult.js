angular.module('learny').controller(
        'lastResultController',
        [
                '$scope',
                '$state',
                'serverCommunicator',
                'test',
                'questions',
                function($scope, $state, serverCommunicator, test, questions) {
                    $scope.test = test.data;
                    $scope.test.questions = questions.data;
                    
                } ]);
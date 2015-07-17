angular.module('learny').controller(
        'testStatsController',
        [
                '$scope',
                '$state',
                'test',
                'questions',
                function($scope, $state, test, questions) {
                    $scope.test = test.data;
                    $scope.test.questions = questions.data;
                } ]);
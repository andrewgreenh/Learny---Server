angular.module('learny').controller(
        'highscoreController',
        [ '$scope', '$state', 'test', 'serverCommunicator', 'top10',
                function($scope, $state, test, serverCommunicator, top10) {
                    $scope.top = top10.data;
                    $scope.test = test.data;
                } ]);
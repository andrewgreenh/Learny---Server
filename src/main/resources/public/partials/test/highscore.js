angular.module('learny').controller(
        'highscoreController',
        [ '$scope', '$state', 'serverCommunicator', 'top10',
                function($scope, $state, serverCommunicator, top10) {
                    $scope.top = top10.data;
                } ]);
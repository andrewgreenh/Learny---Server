angular.module('learny').controller('app',
        [ '$scope', 'loggedIn', 'currentUser', function($scope, loggedIn, currentUser) {
            $scope.loggedIn = loggedIn.value;
            $scope.currentUser = currentUser.value.data;

        } ]);
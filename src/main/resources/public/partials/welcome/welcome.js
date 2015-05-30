angular.module('learny').controller('welcomeController', [ '$scope', function($scope) {
    $scope.showlogin = false;

    $scope.toggleLogin = function() {
        $scope.showlogin = !$scope.showlogin;
    }
    
} ]);
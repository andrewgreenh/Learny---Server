angular.module('learny').directive('learnyheader', function() {
    return {
        restrict : 'E',
        templateUrl : 'script/Learny-header.tpl.html',
        scope : true,
        controller : [ '$scope', function($scope) {

            $scope.showHeader = true;

        } ],

    }
});
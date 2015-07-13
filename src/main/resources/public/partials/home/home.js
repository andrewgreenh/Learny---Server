angular.module('learny').controller('homeController',
        [ '$scope', 'subjects', function($scope, subjects) {
            
            $scope.subjects = subjects.data;
        
        } ]);
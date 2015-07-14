angular.module('learny').controller('homeController',
        [ '$scope', 'subjects', 'adminSubjects', function($scope, subjects, adminSubjects) {
            
            $scope.subjects = subjects.data;
            $scope.adminSubjects = adminSubjects.data;
            $scope.showAdminSucjects = false;
            
            if($scope.adminSubjects.length > 0) {
                $scope.showAdminSubjects = true;
            }
        
        } ]);
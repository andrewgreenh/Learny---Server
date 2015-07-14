angular.module('learny').controller(
        'homeController',
        [ '$scope', 'subjects', 'adminSubjects', 'subjectsAdministratedByUser',
                function($scope, subjects, adminSubjects, subjectsAdministratedByUser) {

                    $scope.subjects = subjects.data;
                    $scope.adminSubjects = adminSubjects.data;
                    $scope.showAdminSucjects = false;
                    $scope.showSucjects = false;

                    if ($scope.adminSubjects.length > 0) {
                        $scope.showAdminSubjects = true;
                    }
                    if ($scope.subjects.length > 0) {
                        $scope.showSubjects = true;
                    }
                } ]);
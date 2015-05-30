angular.module('learny').controller('subjectController',
        [ '$scope', 'serverCommunicator', 'tests', 'subject', function($scope, serverCommunicator, tests, subject) {

            $scope.tests = tests.value.data;
            $scope.subject = subject.value.data;

        } ]);
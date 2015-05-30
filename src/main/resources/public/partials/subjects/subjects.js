angular.module('learny').controller('subjectsController', [ '$scope', 'serverCommunicator', 'subjects',  function($scope, serverCommunicator, subjects) {
    
    $scope.subjects = subjects.value.data;
    
} ]);
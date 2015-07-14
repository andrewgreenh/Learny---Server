angular.module('learny').controller('subjectsController', [ '$scope', '$state', 'serverCommunicator', 'subjects',  function($scope, $state, serverCommunicator, subjects) {
    
    $scope.subjects = subjects.value.data;
    
    $scope.addSubject = function() {
        serverCommunicator.addSubjectAsync({name: $scope.subjectname}).then(function(data){
            $state.go('app.subject', {id: data.data});
        });
    }
    if($scope.currentUser.isGlobalAdmin()) {
        $scope.isAdmin = true;
    }
} ]);
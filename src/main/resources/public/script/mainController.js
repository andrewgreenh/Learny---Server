angular.module('learny').controller('mainController', ['$scope', '$location', 'serverCommunicator', function($scope, $location, serverCommunicator){
  
	serverCommunicator.isLoggedIn().error(function(data){
		$location.path('/login');
	});


}]);
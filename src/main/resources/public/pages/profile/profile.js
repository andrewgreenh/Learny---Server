angular.module('learny').controller('profileController',
		[ 'serverCommunicator', '$scope', function(serverCommunicator, $scope) {
			
			serverCommunicator.getCurrentUserAsync().success(
					function(data, status, headers, config) {
						$scope.username = data.accountName;
						$scope.id = data.id;
					}).error(
					function(data, status, headers, config) {
						console.log('Request failed');
					});;
			
		}
		]);
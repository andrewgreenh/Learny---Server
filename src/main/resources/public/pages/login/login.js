angular.module('learny').controller('loginController',
		[ '$scope', '$location', 'serverCommunicator', function($scope, $location, serverCommunicator) {
			
			$scope.login = function() {
				serverCommunicator.loginAsync($scope.username,$scope.password)
						.success(function(data, status,headers, config) {
							console.log('Successfully logged in');
							$location.path('/');		
						})
						.error(function(data, status, headers, config) {
							console.log('Login failed');
						});
			};

			
		}
		]);
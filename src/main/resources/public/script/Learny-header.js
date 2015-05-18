angular
		.module('learny')
		.directive(
				'learnyheader',
				function() {
					return {
						restrict : 'E',
						templateUrl : 'script/Learny-header.tpl.html',
						scope : true,
						controller : [
								'$scope',
								'$location',
								'serverCommunicator',
								function($scope, $location, serverCommunicator) {
									
									$scope.showHeader = $location.path() !== '/login';
									
									
									$scope.logout = function() {
										serverCommunicator
												.logoutAsync()
												.success(
														function(data, status,
																headers, config) {
															console
																	.log('Successfully logged out');
															$location.path('/login');
														})
												.error(
														function(data, status,
																headers, config) {
															console
																	.log('Logout failed');
														});
									}
								} ],

					}
				});
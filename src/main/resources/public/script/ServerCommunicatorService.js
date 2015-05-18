angular.module('learny').factory(
		'serverCommunicator',
		[
				'$http',
				function($http) {
					var service = {};

					service.loginAsync = function(username, password) {
						return $http.post('/login?username='
								+ encodeURI(username) + '&password='
								+ encodeURI(password));
					};

					service.logoutAsync = function() {
						return $http.get('/logout');
					};

					service.getCurrentUserAsync = function() {
						return $http.get('/api/account/me');
					};

					service.isLoggedIn = function() {
						return $http.get('/api/account/loggedin');
					};

					return service;
				} ]);
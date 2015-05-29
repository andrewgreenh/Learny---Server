var app = angular.module('learny', [ 'ui.router' ]);

app.config(function($stateProvider, $urlRouterProvider) {

    $stateProvider

    .state(
            'app',
            {
                url : '/app',
                resolve : {
                    loggedIn : function(serverCommunicator, $state) {
                        return serverCommunicator.isLoggedInAsync().then(
                                function(data, status, headers, config) {
                                    return {
                                        value : true
                                    };
                                }, function(data, status, headers, config) {
                                    $state.go('login');
                                    return {
                                        value : false
                                    };

                                });
                    },
                    currentUser : function(serverCommunicator, $state) {
                        return serverCommunicator.getCurrentUserAsync().then(
                                function(data, status, headers, config) {
                                    return {
                                        value : data
                                    };
                                }, function(data, status, headers, config) {
                                    $state.go('login');
                                    return {
                                        value : false
                                    }
                                });
                    }
                },
                controller : 'app',
                templateUrl : 'partials/app/app.html'
            })

    .state('app.profile', {
        url : '/profile',
        controller : 'profileController',
        resolve : {},
        templateUrl : 'partials/profile/profile.html'
    })

    .state('login', {
        url : '/login',
        resolve : {},
        controller : 'loginController',
        templateUrl : 'partials/login/login.html'
    })

    .state('app.home', {
        url : '/home',
        resolve : {},
        controller : 'homeController',
        templateUrl : 'partials/home/home.html'
    });

    $urlRouterProvider.otherwise("/app");

});

app.run(function($rootScope, $state, serverCommunicator) {
    $rootScope.$on('$stateChangeStart', function(event, toState, toParams, fromState, fromParams) {
        if (toState.name !== 'login') {
            serverCommunicator.isLoggedInAsync().error(function() {
                $state.go('login');
            });
        }
    })
});

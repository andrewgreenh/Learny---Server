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
                    currentUser : function(serverCommunicator) {
                        return serverCommunicator.getCurrentUserAsync().then(
                                function(data, status, headers, config) {
                                    return {
                                        value : data
                                    };
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

    .state(
            'app.subjects',
            {
                url : '/subjects',
                controller : 'subjectsController',
                resolve : {
                    subjects : function(serverCommunicator) {
                        return serverCommunicator.getSubjectsAsync().then(
                                function(data, status, headers, config) {
                                    return {
                                        value : data
                                    };
                                });
                    }
                },
                templateUrl : 'partials/subjects/subjects.html'
            })

    .state(
            'app.subject',
            {
                url : '/subject/:id',
                controller : 'subjectController',
                resolve : {
                    tests : function($stateParams, serverCommunicator) {
                        return serverCommunicator.getTestsOfSubjectAsync($stateParams.id).then(
                                function(data, status, headers, config) {
                                    return {
                                        value : data
                                    };
                                });
                    },
                    subject : function($stateParams, serverCommunicator) {
                        return serverCommunicator.getSubjectAsync($stateParams.id).then(
                                function(data, status, headers, config) {
                                    return {
                                        value : data
                                    };
                                });
                    }
                },
                templateUrl : 'partials/subject/subject.html'
            })

    .state('login', {
        url : '/login',
        resolve : {},
        templateUrl : 'partials/login/login.html'
    })

    .state('welcome', {
        url : '/',
        resolve : {},
        templateUrl : 'partials/welcome/welcome.html'
    })

    .state('app.home', {
        url : '/',
        resolve : {},
        controller : 'homeController',
        templateUrl : 'partials/home/home.html'
    });

    $urlRouterProvider.otherwise("/");

});

app.run(function($rootScope, $state, serverCommunicator) {
    $rootScope.$on('$stateChangeStart', function(event, toState, toParams, fromState, fromParams) {
        if (toState.name !== 'login' && toState.name !== 'welcome') {
            serverCommunicator.isLoggedInAsync().error(function() {
                $state.go('login');
            });
        } else if (toState.name == 'login') {
            serverCommunicator.isLoggedInAsync().success(function() {
                $state.go('app.home');
            });
        }
    })
});

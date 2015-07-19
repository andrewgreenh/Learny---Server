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
                                    $state.go('welcome');
                                    return {
                                        value : false
                                    };

                                });
                    },
                    currentUser : function(serverCommunicator) {
                        var user = {};
                        return serverCommunicator.getCurrentUserAsync().then(
                                function(data, status, headers, config) {
                                    return data.data;
                                });
                    },
                    subjectsAdministratedByUser : function(serverCommunicator) {
                        return serverCommunicator.getAdministratedSubjectsAsync().then(
                                function(data, status, headers, config) {
                                    return data.data.map(function(item) {
                                        return item.id;
                                    });
                                });
                    },
                    subjectsUserIsEnroledIn : function(serverCommunicator) {
                        return serverCommunicator.getEnroledSubjectsAsync().then(
                                function(data, status, headers, config) {
                                    return data.data.map(function(item) {
                                        return item.id;
                                    });
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

    .state('app.editProfile', {
        url : '/profile/edit',
        controller : 'editProfileController',
        resolve : {},
        templateUrl : 'partials/profile/editProfile.html'
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
                                    data.data.sort(function(subjectA, subjectB) {
                                        if (subjectA.name < subjectB.name)
                                            return -1;
                                        if (subjectA.name > subjectB.name)
                                            return 1;
                                        return 0;
                                    });
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
                                    data.data.sort(function(testA, testB) {
                                        if (testA.name < testB.name)
                                            return -1;
                                        if (testA.name > testB.name)
                                            return 1;
                                        return 0;
                                    });
                                    return {
                                        value : data
                                    };
                                });
                    },
                    testScores : function($stateParams, serverCommunicator) {
                      return serverCommunicator.getMyTestScoresOfSubjectAsync($stateParams.id).then(function(data) {
                          return data;
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

    .state(
            'app.test',
            {
                url : '/test/:id',
                controller : 'testController',
                resolve : {
                    test : function($stateParams, serverCommunicator) {
                        return serverCommunicator.getTestAsync($stateParams.id).then(
                                function(data, status, headers, config) {
                                    return data;
                                });
                    },
                    questions : function($stateParams, serverCommunicator) {
                        return serverCommunicator.getQuestionsToTestAsync($stateParams.id).then(
                                function(data, status, headers, config) {
                                    data.data.sort(function(questionA, questionB) {
                                        if (questionA.id < questionB.id)
                                            return -1;
                                        if (questionA.id > questionB.id)
                                            return 1;
                                        return 0;
                                    });
                                    data.data.forEach(function(item) {
                                        item.answers.sort(function(answerA, answerB) {
                                            return answerA.id - answerB.id;
                                        });
                                    });
                                    return data;
                                });
                    }
                },
                templateUrl : 'partials/test/test.html'
            })
            
            .state(
            'app.testStats',
            {
                url : '/test/:id/stats',
                controller : 'testStatsController',
                resolve : {
                    test : function($stateParams, serverCommunicator) {
                        return serverCommunicator.getTestAsync($stateParams.id).then(
                                function(data, status, headers, config) {
                                    return data;
                                });
                    },
                    questions : function($stateParams, serverCommunicator) {
                        return serverCommunicator.getQuestionsToTestAsync($stateParams.id).then(
                                function(data, status, headers, config) {
                                    data.data.sort(function(questionA, questionB) {
                                        if (questionA.id < questionB.id)
                                            return -1;
                                        if (questionA.id > questionB.id)
                                            return 1;
                                        return 0;
                                    });
                                    data.data.forEach(function(item) {
                                        item.answers.sort(function(answerA, answerB) {
                                            return answerA.id - answerB.id;
                                        });
                                    });
                                    return data;
                                });
                    }
                },
                templateUrl : 'partials/test/testStats.html'
            }).state(
            'app.highscore',
            {
                url : '/test/:id/highscore',
                controller : 'highscoreController',
                resolve : {
                    top10 : function($stateParams, serverCommunicator) {
                        return serverCommunicator.getHighscoreFromTestAsync($stateParams.id).then(
                                function(data, status, headers, config) {
                                    return data;
                                });
                    }
                },
                templateUrl : 'partials/test/highscore.html'
            })

    .state(
            'app.lastResult',
            {
                url : '/test/:id/lastresult',
                controller : 'lastResultController',
                resolve : {
                    test : function($stateParams, serverCommunicator) {
                        return serverCommunicator.getTestAsync($stateParams.id).then(
                                function(data, status, headers, config) {
                                    return data;
                                });
                    },
                    result : function($stateParams, serverCommunicator) {
                        return serverCommunicator.getLatestTestresultAsync($stateParams.id).then(
                                function(data, status, headers, config) {
                                    return data;
                                });
                    },
                    questions : function($stateParams, serverCommunicator) {
                        return serverCommunicator.getQuestionsToTestAsync($stateParams.id).then(
                                function(data, status, headers, config) {
                                    data.data.sort(function(questionA, questionB) {
                                        if (questionA.id < questionB.id)
                                            return -1;
                                        if (questionA.id > questionB.id)
                                            return 1;
                                        return 0;
                                    });
                                    data.data.forEach(function(item) {
                                        item.answers.sort(function(answerA, answerB) {
                                            return answerA.id - answerB.id;
                                        });
                                    });
                                    return data;
                                });
                    }
                },
                templateUrl : 'partials/test/lastResult.html'
            })

    .state(
            'app.createTest',
            {
                url : '/subject/:id/createTest',
                controller : 'createTestController',
                resolve : {
                    subject : function($stateParams, serverCommunicator) {
                        return serverCommunicator.getSubjectAsync($stateParams.id).then(
                                function(data, status, headers, config) {
                                    return data;
                                });
                    }
                },
                templateUrl : 'partials/test/createTest.html'
            })
            
    .state(
            'app.personList',
            {
                url : '/admin/users',
                params: {
                    success: null,
                  },
                controller : 'personListController',
                resolve : {
                    users : function(serverCommunicator) {
                        return serverCommunicator.getAllUsersAsync().then(
                                function(data, status, headers, config) {
                                    return data;
                                });
                    }
                },
                templateUrl : 'partials/personAdministration/personList.html'
            })

    .state('createAccount', {
        url : '/register',
        resolve : {},
        controller : 'createAccountController',
        templateUrl : 'partials/createAccount/createAccount.html'
    })

    .state('welcome', {
        url : '/',
        resolve : {},
        templateUrl : 'partials/welcome/welcome.html'
    })

    .state(
            'app.home',
            {
                url : '/',
                resolve : {
                    subjects : function(serverCommunicator) {
                        return serverCommunicator.getEnroledSubjectsAsync().then(
                                function(data, status, headers, config) {
                                    data.data.sort(function(subjectA, subjectB) {
                                        if (subjectA.name < subjectB.name)
                                            return -1;
                                        if (subjectA.name > subjectB.name)
                                            return 1;
                                        return 0;
                                    });
                                    return data;
                                });
                    },
                    adminSubjects : function(serverCommunicator) {
                        return serverCommunicator.getAdministratedSubjectsAsync().then(
                                function(data, status, headers, config) {
                                    data.data.sort(function(subjectA, subjectB) {
                                        if (subjectA.name < subjectB.name)
                                            return -1;
                                        if (subjectA.name > subjectB.name)
                                            return 1;
                                        return 0;
                                    });
                                    return data;
                                });
                    }
                },
                controller : 'homeController',
                templateUrl : 'partials/home/home.html'
            });

    $urlRouterProvider.otherwise("/");

});

app.run(function($rootScope, $state, serverCommunicator) {
    $rootScope.$on('$stateChangeStart', function(event, toState, toParams, fromState, fromParams) {
        if (stateShouldBeBehindLogin(toState.name)) {
            serverCommunicator.isLoggedInAsync().error(function() {
                $state.go('welcome');
            });
        }
        $('.navbar-collapse').collapse('hide');
    })
});

function stateShouldBeBehindLogin(state) {
    if ([ 'welcome', 'createAccount' ].indexOf(state) > -1) {
        return false
    }
    return true;
}

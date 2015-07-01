angular.module('learny').factory(
        'serverCommunicator',
        [
                '$http',
                function($http) {
                    var service = {};

                    service.createAccountAsync = function(data) {
                        return $http.post('/api/accounts', data);
                    }

                    service.loginAsync = function(username, password) {
                        return $http.post('/login?username=' + encodeURI(username) + '&password='
                                + encodeURI(password));
                    };

                    service.logoutAsync = function() {
                        return $http.get('/logout');
                    };

                    service.getCurrentUserAsync = function() {
                        return $http.get('/api/accounts/me');
                    };

                    service.getSubjectsAsync = function() {
                        return $http.get('/api/subjects');
                    };

                    service.getTestsOfSubjectAsync = function(subjectId) {
                        return $http.get('/api/subjects/' + subjectId + '/tests');
                    };

                    service.getSubjectAsync = function(subjectId) {
                        return $http.get('/api/subjects/' + subjectId);
                    };

                    service.isLoggedInAsync = function() {
                        return $http.get('/api/accounts/loggedin');
                    };

                    service.updateProfileAsync = function(userId, data) {
                        return $http.put('/api/accounts/' + userId, data);
                    }

                    service.getAdministratedSubjectsAsync = function() {
                        return $http.get('/api/accounts/me/administrated-subjects');
                    }

                    service.addUserAsAdministratorToSubjectAsync = function(accountName, subjectId) {
                        return $http.post('/api/subjects/' + subjectId + '/responsibles', {
                            accountName : accountName
                        });
                    }
                    
                    service.removeAdministratorFromSubjectAsync = function(userId, subjectId) {
                        return $http.delete('/api/subjects/' + subjectId + '/responsibles/' + userId);
                    }

                    service.getEnroledSubjectsAsync = function() {
                        return $http.get('/api/accounts/me/enroled-subjects');
                    }

                    service.joinSubjectAsync = function(subject) {
                        return $http.post('/api/accounts/me/enroled-subjects', subject);
                    }
                    
                    service.deleteTestAsync = function(testId) {
                        return $http.delete('/api/tests/'+ testId);
                    }
                    
                    service.getTestAsync = function(testId) {
                        return $http.get('/api/tests/'+ testId);
                    }
                    
                    service.getQuestionsToTestAsync = function(testId) {
                        return $http.get('/api/tests/'+ testId + '/questions');
                    }
                    
                    service.addTestToSubject = function(test, subjectId) {
                        return $http.post('/api/subjects/'+ subjectId + '/tests', test);
                    }
                    
                    service.turnInAnswersToTestAsync = function(answers, testId) {
                        return $http.post('/api/tests/' + testId + '/results', answers);
                    }
                    return service;
                } ]);
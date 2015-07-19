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
                        return $http.post('/login?username=' + encodeURIComponent(username) + '&password='
                                + encodeURIComponent(password));
                    };

                    service.logoutAsync = function() {
                        return $http.get('/logout');
                    };

                    service.getCurrentUserAsync = function() {
                        return $http.get('/api/accounts/me');
                    };
                    
                    service.deleteUserAsync = function(user) {
                        return $http.delete('api/accounts/' + user.id);
                    }
                    
                    service.updatePasswordAsync = function(oldPassword, newPassword) {
                        return $http.put('api/accounts/me/password?oldPassword=' + encodeURIComponent(oldPassword) + '&newPassword=' + encodeURIComponent(newPassword));
                    }
                    
                    service.resetPasswordAsync = function(token, newPassword) {
                        return $http.post('api/accounts/password/reset?password=' + encodeURIComponent(newPassword) + '&token=' + token);
                    }
                    
                    service.requestPasswordAsync = function(email) {
                        return $http.post('api/accounts/password/requestToken?mail=' + encodeURIComponent(email));
                    }
                    
                    service.updateRolesAsync = function(user) {
                        return $http.put('/api/accounts/' + user.id + '/updateRole', user.newRoles);
                    };
                    
                    service.getAllUsersAsync = function() {
                        return $http.get('/api/accounts');
                    };
                    
                    service.findDozentenAsync = function(string) {
                        return $http.get('/api/accounts/findwithrole/' + string + '&dozent');
                    };

                    service.getSubjectsAsync = function() {
                        return $http.get('/api/subjects');
                    };

                    service.getTestsOfSubjectAsync = function(subjectId) {
                        return $http.get('/api/subjects/' + subjectId + '/tests');
                    };

                    service.getMyTestScoresOfSubjectAsync = function(subjectId) {
                        return $http.get('/api/accounts/me/testResultsForSubject/' + subjectId);
                    }
                    
                    service.getSubjectAsync = function(subjectId) {
                        return $http.get('/api/subjects/' + subjectId);
                    };
                    
                    service.addSubjectAsync = function(subject) {
                        return $http.post('/api/subjects/', subject);
                    };
                    
                    service.updateSubjectAsync = function(subject) {
                        return $http.put('/api/subjects/' + subject.id, subject);
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
                    
                    service.leaveSubjectAsync = function(subject) {
                        return $http.delete('/api/accounts/me/enroled-subjects/' + subject.id);
                    }
                    
                    service.deleteTestAsync = function(testId) {
                        return $http.delete('/api/tests/'+ testId);
                    }
                    
                    service.getTestAsync = function(testId) {
                        return $http.get('/api/tests/'+ testId);
                    }
                    
                    service.getHighscoreFromTestAsync = function(testId) {
                        return $http.get('/api/tests/'+ testId + '/highscore');
                    }
                    
                    service.getQuestionsToTestAsync = function(testId) {
                        return $http.get('/api/tests/'+ testId + '/questions');
                    }
                    
                    service.getLatestTestresultAsync = function(testId) {
                        return $http.get('/api/tests/'+ testId + '/myLatestResult');
                    }
                    
                    service.addTestToSubject = function(test, subjectId) {
                        return $http.post('/api/subjects/'+ subjectId + '/tests', test);
                    }
                    
                    service.turnInTestAsync = function(test) {
                        return $http.post('/api/tests/' + test.id + '/results', test.questions);
                    }
                    return service;
                } ]);
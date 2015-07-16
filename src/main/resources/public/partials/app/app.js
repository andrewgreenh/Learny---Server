angular.module('learny').controller(
        'app',
        [
                '$scope',
                'loggedIn',
                'currentUser',
                'subjectsAdministratedByUser',
                'subjectsUserIsEnroledIn',
                function($scope, loggedIn, currentUser, subjectsAdministratedByUser,
                        subjectsUserIsEnroledIn) {
                    $scope.loggedIn = loggedIn.value;
                    $scope.currentUser = currentUser;
                    $scope.currentUser.administratedSubjects = subjectsAdministratedByUser;
                    $scope.currentUser.enroledSubjects = subjectsUserIsEnroledIn;
                    $scope.setUser = function(user) {
                        $scope.currentUser = user;
                    }
                    
                    $scope.currentUser.roles = flattenRoles($scope.currentUser.roles);
                    
                    $scope.currentUser.isAdminOf = function(subjectId) {
                        if($scope.currentUser.administratedSubjects.indexOf(subjectId) > -1) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                    
                    $scope.currentUser.isGlobalAdmin = function() {
                        if($scope.currentUser.roles.indexOf("admin") > -1) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                    
                    $scope.currentUser.isEnroledIn = function(subjectId) {
                        if ($scope.currentUser.enroledSubjects.indexOf(subjectId) > -1) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                    
                    $scope.globalModalFunction = undefined;
                    $scope.globalModalText = "";
                    $scope.global = {};
                    
                    $scope.global.showGlobalModal = function(text, callback) {
                        $scope.globalModalText = text;
                        $scope.globalModalFunction = callback;
                        $('#globalModal').modal();
                    }
                    
                    function flattenRoles(array) {
                        var result = [];
                        for(var i = 0; i < array.length; i++) {
                            result = result.concat(array[i].name);
                        }
                        return result;
                    }

                } ]);
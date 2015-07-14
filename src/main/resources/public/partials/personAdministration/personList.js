angular.module('learny').controller(
        'personListController',
        [
                '$scope',
                '$state',
                '$stateParams',
                '$timeout',
                'serverCommunicator',
                'users',
                function($scope, $state, $stateParams, $timeout, serverCommunicator, users) {
                    $scope.showSuccess = $stateParams.success;
                    $timeout(function(){$scope.showSuccess = false;}, 2000);
                    users.data.forEach(function(user) {
                        user.roles = flattenRoles(user.roles);
                        user.isDozent = false;
                        user.isAdmin = false;
                        if (user.roles.indexOf("dozent") > -1) {
                            user.isDozent = true;
                        }
                        if (user.roles.indexOf("admin") > -1) {
                            user.isAdmin = true;
                        }
                    });
                    $scope.users = users.data;
                    $scope.filter = "";
                    $scope.filterFunction = function(user) {
                        return user.accountName.indexOf($scope.filter.replace(/\s+/g, '')
                                .toLowerCase()) > -1;
                    }
                    
                    $scope.updateRoles = function(user) {
                        var newRoles = [];
                        if(user.isAdmin) {
                            newRoles.push({name: "admin"});
                        }
                        if(user.isDozent) {
                            newRoles.push({name: "dozent"});
                        }
                        newRoles.push({name: "user"});
                        user.newRoles = newRoles;
                        serverCommunicator.updateRolesAsync(user).then(function(){
                            $state.go($state.current, {success: true}, {
                                reload : true
                            });
                        });

                    }
                    
                    function flattenRoles(array) {
                        var result = [];
                        for(var i = 0; i < array.length; i++) {
                            result = result.concat(array[i].name);
                        }
                        return result;
                    }
                } ]);
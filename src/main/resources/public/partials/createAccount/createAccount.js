angular.module('learny').controller(
        'createAccountController',
        [
                '$scope',
                'serverCommunicator',
                function($scope, serverCommunicator) {
                    $scope.create = function() {
                        serverCommunicator.createAccountAsync($scope.user).then(function(){
                            $scope.created = true;
                            $scope.error = false;
                        }, function(data) {
                            if(data.data.message.indexOf('Accountname') > -1) {
                                $scope.errorMsg = "Accountname ist schon vorhanden!";
                            } else if(data.data.message.indexOf('E-Mail') > -1) {
                                $scope.errorMsg = "Die E-Mail Adresse ist schon vorhanden!";
                            }
                            $scope.error = true;
                            $scope.created = false;
                        });
                    }

                } ]);
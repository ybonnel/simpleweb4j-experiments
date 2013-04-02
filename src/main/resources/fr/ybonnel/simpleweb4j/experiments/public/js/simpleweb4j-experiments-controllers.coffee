
MainCtrl = ($scope, $http) ->
    $http.get("/hello").success (data) ->
        $scope.hello = data.value



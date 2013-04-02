
/**
 * Declare MainCtrl, this controller does a GET on "/hello" and put the result in scope.
 */
function MainCtrl($scope, $http) {
    $http.get("/hello").success(function(data) {
        $scope.hello = data.value;
    });
}
MainCtrl.$inject = [ '$scope', '$http' ];

/**
 * Declare the routes.
 * Route /main (#/main in browser) use the controller MainCtrl with template main.html
 */
angular.module('simpleweb4j-experiments', []).config(['$routeProvider', function($routeProvider) {
    $routeProvider.when('/main', {templateUrl:'partial/main.html', controller:MainCtrl});
    $routeProvider.otherwise({redirectTo: '/main'});
}]);
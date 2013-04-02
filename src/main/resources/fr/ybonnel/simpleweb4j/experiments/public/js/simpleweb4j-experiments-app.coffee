# "use strict";

angular
    .module( 'simpleweb4j-experiments', [ ] )
    .config( ['$routeProvider', ($routeProvider) ->
        $routeProvider
        .when( '/main',
            templateUrl: 'partial/main.html'
            controller: MainCtrl
        )
        .otherwise(
            redirectTo: '/main'
        )
    ])
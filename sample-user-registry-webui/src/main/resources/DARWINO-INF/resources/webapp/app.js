
(function(){
	
	"use strict";
	
	angular.module('UserRegistry', ['ngResource', 'ui.router'])
		.config(function($urlRouterProvider, $stateProvider) {
				$urlRouterProvider.otherwise("/landing/decision");
				
				$stateProvider
				    .state('landing', {
				      url: "/landing",
				      templateUrl: "app/landing/landing.html",
				      controller: "LandingController as landing"
				    });
		});
})();




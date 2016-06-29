(function() {

	"use strict";

	angular.module('UserRegistry').config(function($stateProvider) {
		$stateProvider.state('landing.decision', {
			url : "/decision",
			templateUrl : "app/landing/decision/decision.html"
		}).state('landing.login', {
			url : "/login",
			templateUrl : "app/landing/login/login.html",
		      controller: "LoginController as login"
		}).state('landing.register', {
			url : "/register",
			templateUrl : "app/landing/register/register.html",
		      controller: "RegistrationController as register"
		}).state('landing.activate', {
			url : "/activate/:confirmationId",
			templateUrl : "app/landing/activate/activate.html",
		      controller: "ActivationController as activate"
		});
	});
})();

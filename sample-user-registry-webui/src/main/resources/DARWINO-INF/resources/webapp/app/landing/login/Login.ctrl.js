(function(){
	
	"use strict";
	
	angular.module('UserRegistry')
		.controller('LoginController', function($state) {
			
			var vm = this;
			
			
			vm.email = null;
			vm.password = null;
			
			vm.login = function(){
				console.log("login with: " + vm.email + " : " + vm.password);
				$state.go('inside');
			}
		});
})();
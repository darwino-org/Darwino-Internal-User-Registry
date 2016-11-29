(function(){
	
	"use strict";
	
	angular.module('UserRegistry')
		.controller('RegistrationController', function($state) {
			
			var vm = this;
			
			vm.data = {
					firstname: null,
					lastname: null,
					email: null,
					password: null,
					confirmation: null
			};
			
			vm.register = function(){
				console.log("registering:");
				console.log(vm.data);
				$state.go("landing.activate", {confirmationId: vm.data.firstname + vm.data.lastname});
			}
			
			
			
			
			
		});
})();
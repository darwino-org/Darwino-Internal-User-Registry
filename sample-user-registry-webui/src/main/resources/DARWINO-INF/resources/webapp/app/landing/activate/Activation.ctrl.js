(function(){
	
	"use strict";
	
	angular.module('UserRegistry')
		.controller('ActivationController', function( $state) {
			
			var vm = this;
			vm.confirmId = $state.params.confirmationId;
			
			vm.activate = function(){
				console.log("activated with id: " + vm.confirmId);
				$state.go('inside');
			}
			
			
			
			
			
		});
})();
(function() {

	"use strict";

	angular.module('app').directive('msgBox', function() {

		function msgBoxController() {
			var vm = this;
			
			vm.getDisplayClass = function(err){
				var base = "alert ";
				if ( !err.type || err.type == "danger"){
					return base + "alert-danger";
				};
				return base + "alert-" + err.type;
			};
			
			return vm;
		}

		var directive = {
			restrict : 'E',
			templateUrl : 'components/msgbox/msgBox.html',
			scope : {
				error : '=',
				tpye : '=',
				messages : '=',
				trace : '='
			},
			controller : [ msgBoxController],
			controllerAs : 'vm',
			bindToController : true
		};

		return directive;
	});
})();
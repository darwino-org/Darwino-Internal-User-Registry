/**
 * Fields send to GUI: firstname lastname email password
 * 
 * action: getprofile
 * 
 * Fields send to GUI: firstname lastname email password
 * 
 * action updateprofile
 */

packFlowControllers.controller('ProfileController', function($scope, $http, $location) {

	$scope.$watch('getMyUserProfile', function() {
		// What's my unique- / user-id if I've authenticated against tomcat???
		$http.get('api/profileservice?action=getmyprofile').success(function(data, status, headers, config) {
			$scope.userProfile = data;
		
		}).error(function(data, status, headers, config) {
			showGeneralError();
		});
	});

	$scope.$watch('$viewContentLoaded', function() {
		if ($location.absUrl().indexOf("activateprofile") > -1) {
			var activationId = $location.search().activateprofile;
			if (activationId != undefined && activationId != "") {

				$http({
					url : 'api/profileservice?action=activateprofile',
					method : "POST",
					headers : {
						"Content-Type" : "application/json"
					},
					data : {
						id : activationId,
					}
				}).success(function(data, status, headers, config) {
					// analyze return value
					if (data.status === "OK") {
						$scope.activated = true;
					} else {
						alert(data.status + ": " + data.message);
					}
				}).error(function(data, status, headers, config) {
					// handle error (how?? Talk with lena?)
					showGeneralError();
				});
			}
		} else {
			return;
		}

		// da die id auslesen und dann das profil aktivieren...
		// https://docs.angularjs.org/api/ngRoute/service/$routeParams =>
		// Grosses ???
	});

	$scope.getUserProfile = function() {
		$http.get('api/profileservice?action=getprofile&id=' + $scope.userProfile.id).success(function(data, status, headers, config) {
			if (data.status === "OK") {
				$scope.userProfile = data.userProfile;
			} else {
				alert(data.status + ": " + data.message);
			}
		}).error(function(data, status, headers, config) {
			showGeneralError();
		});
	};

	$scope.updateUserProfile = function() {
		// update user profile request
		$scope.submitted = false;
		$scope.register = function() {
			// check of email && confirmation
			checkPasswordMatch();
			checkEmail();
			// validation of fields
			if ($scope.profileForm.$valid) {
				// post to UserProfileServiceAPI
				$http({
					url : 'api/profileservice?action=updateprofile&id=' + $scope.userProfile.id,
					method : "POST",
					headers : {
						"Content-Type" : "application/json"
					},
					data : {
						userprofile : $scope.userProfile,
					}
				}).success(function(data, status, headers, config) {
					// analyze return value
					if (data.status === "OK") {
						$scope.submitted = true;
					} else {
						alert(data.status + ": " + data.message);
					}
				}).error(function(data, status, headers, config) {
					// handle error (how?? Talk with lena?)
					showGeneralError();
				});
			} else {
				return;
			}
		}
	}

		// register request
		$scope.submitted = false;
		$scope.register = function() {
			// check of email && confirmation
			checkPasswordMatch();
			checkEmail();
			// validation of fields
			if ($scope.registrationForm.$valid) {
				// post to RegistrationServiceAPI
				$http({
					url : 'api/profileservice?action=register',
					method : "POST",
					headers : {
						"Content-Type" : "application/json"
					},
					data : {
						firstname : $scope.firstname,
						lastname : $scope.lastname,
						email : $scope.email,
						password : $scope.password,
						confirmation : $scope.confirmation,
						accept : $scope.accept
					}
				}).success(
				// analyze return value
				function(data, status, headers, config) {
					var status = data.status;
					var message = data.message;
					if (status === "OK") {
						$scope.submitted = true;
					} else {
						alert(message);
					}
				}).error(
				// handle error (how?? Talk with lena?)
				function(data, status, headers, config) {
					alert('An error occurred while processing your request!');
				});
			} else {
				return;
			}

		}

		
	

	// Feel free to convert this into a directive
	function checkPasswordMatch() {
		var match = true;
		if ($scope.password != undefined && $scope.confirmation != undefined) {
			match = $scope.password == $scope.confirmation;
		}

		$scope.registrationForm.confirmation.$setValidity("match", match);
		return;
	}

	// Feel free to convert this into a directive
	function checkEmail() {
		var EMAIL_REGEXP = /^[_a-z0-9]+(\.[_a-z0-9]+)*@[a-z0-9-]+(\.[a-z0-9-]+)*(\.[a-z]{2,4})$/;
		var valid = true;
		if ($scope.email != undefined) {
			valid = EMAIL_REGEXP.test($scope.email);
		}
		return $scope.registrationForm.email.$setValidity("email", valid);
	}
	
	function showGeneralError() {
		alert('An error occurred while processing your request!');
	}

});
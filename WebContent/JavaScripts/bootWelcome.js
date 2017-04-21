(function(){
	
	var app = angular.module('logInApp',['menu']);
	
	JsonUser1 = {
			
			username:"Henry",
			password:"2",
			name:"3",
			surname:"4",
			email:"w@w.com",
			phoneNumber:696969,
			address:"7",
			latitude:9,
			longtitude:9,
			afm:20,
			country:"Greece",
			location:"Athens",

	};
	
	app.controller('SignController',['$scope','$http',function($scope,$http)
	{
		
		$scope.JsonUser = JsonUser1;
		$scope.userNotFound=0;
		$scope.wrongPassWord = 0;
		
		$scope.showSignIn = 1;
		$scope.showSignUp = 0;
		$scope.signUpComplete=0;
		

		$scope.confDoesNotMatch = 0;
		$scope.usernameExists = 0;
		
		/* metavlhth gia emfanish la8ous se periptws pou den ton exei kanei accept o admin */
		$scope.forbidden = 0;
		
		
		$scope.checkPassword = function(password,passwordConf)
		{
			if( password != passwordConf )
			{
				$scope.confDoesNotMatch = 1;
				return 1;
			}
			else
			{
				$scope.confDoesNotMatch = 0;
				return 0;
			}
				
		}
		
		$scope.checkUserName = function(username)
		{

			req = new XMLHttpRequest();

			var url = encodeURI("services/users/" + username);
			
			req.open("GET",url, false);

			req.onreadystatechange = function()
			{
				if (req.readyState == 4) {
					if (req.status == 302) {
						$scope.usernameExists = 1;
						return 1;
					}
					else
					{
						$scope.usernameExists = 0;
						return 0;
					}
				}
			}

			req.send(null);

		}
		
		$scope.signUp = function()
		{
						
			user = $scope.JsonUser;
			
			/* Elegkse ean yparxei auto to username sth vash */
			var res = $scope.checkUserName(user.username);
			
			/* ean to Usename Yparxei vges apo th synarthsh */
			if( $scope.usernameExists == 1 )
				return;

			passwordConf = document.getElementsByName("passwordConf")[0].value;
			
			res = $scope.checkPassword(user.password,passwordConf);
			
			if( res == 1 )
				return;

			var req = new XMLHttpRequest();

			var url = encodeURI("https://localhost:8443/Project1WithGerryDB/services/users");

			req.open("POST",url,true);

			req.setRequestHeader("Content-type", "application/json");

			req.onreadystatechange = function () { 
				if (req.readyState == 4 && req.status == 200) {
					var json = JSON.parse(req.responseText);
				}
			}

			var data = JSON.stringify(user);

			req.send(data);

			$scope.signUpComplete = 1;
			$scope.showSignIn = 0;
			$scope.showSignUp = 0;
			
		}
		
		
		$scope.signUpPressed = function()
		{
			$scope.showSignIn = 0;
			$scope.showSignUp = 1;
		}
		
		$scope.logIn = function()
		{
			var req = new XMLHttpRequest();
	
			user = JsonUser1;
			user.username = document.getElementById("username").value;
			user.password = document.getElementById("password").value;
			
			var url = encodeURI("https://localhost:8443/Project1WithGerryDB/services/users/logIn");
	
			req.open("POST",url,false);
	
			req.setRequestHeader("Content-type", "application/json");
	
			req.onreadystatechange = function () { 
	
				if (req.readyState == 4)
				{
					if(req.status == 302) 
					{
						$scope.userNotFound = 0;
						$scope.wrongPassWord = 0;
						$scope.forbidden = 0;
						setCookie("username",user.username,10);
						window.location = "https://localhost:8443/Project1WithGerryDB/myIndex.html";
						return;
					}
					
					if(req.status == 404)
					{
						$scope.userNotFound = 0;
						$scope.wrongPassWord = 1;
						$scope.forbidden = 0;
					}
					
					if(req.status == 202)
					{
						$scope.userNotFound = 0;
						$scope.wrongPassWord = 0;
						$scope.forbidden = 0;
						setCookie("username", "admin", 10);
						window.location = "https://localhost:8443/Project1WithGerryDB/adminPage.html";
						return;
					}
					
					if(req.status == 403)
					{
						$scope.forbidden = 1;
						$scope.userNotFound = 0;
						$scope.wrongPassWord = 0;
					}
						
				}
			}
			
			var data = JSON.stringify(user);
			
			req.send(data);
			
		}
	
	}]);
	
})();



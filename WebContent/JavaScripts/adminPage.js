(function(){
	
	var app = angular.module('AdminApp',['menu']);
	
	app.controller('AdminController',['$scope','$http',function($scope,$http)
	{
		
		$scope.users = [];
		
		$scope.getNewUsers = function() 
		{
			
			$http.get("services/newUsers/findNewUsers").success(function(data)
			{
				$scope.users = data;
				
				for (var int = 0; int < $scope.users.length; int++) {
					$scope.users[int].showAccept=true;
					$scope.users[int].showDecline=true;
					$scope.users[int].showInserted=false;
					$scope.users[int].showDeclined=false;
				}
				
			})
		}
		
		$scope.checkIfAdmin = function()
		{
			if(getCookie('username') != 'admin' )
				window.location = "https://localhost:8443/Project1WithGerryDB/BootWelcome.html";
		}
		
		$scope.isAdmin = function()
		{
			return getCookie('username') == 'admin';
		}
		
		
		$scope.inserted = function(user)
		{
			return user.showInserted === true;
		}
		
		$scope.declined = function(user)
		{
			return user.showDeclined === true;
		}
		
		
		$scope.declineNewUser = function(user)
		{
			
			user.showAccept=false;
			user.showDecline=false;
			user.showInserted=false;
			user.showDeclined=true;
			
			var req = new XMLHttpRequest();

			var url = encodeURI("services/newUsers");

			req.open("DELETE",url,true);

			req.setRequestHeader("Content-type", "application/json");
			
			req.onreadystatechange = function()
			{
				if (req.readyState == 4 ) 
				{
					/*
					 * Edw to newUsers prepei na pairnei thn apanthsh ok h' oxi gia ton an mpeike o xrhsths sto systhma
					 */
					if(req.status != 200)
						alert(req.status);
				}
				
			}

//			req.onreadystatechange = function () { 
//				if (req.readyState == 4 && req.status == 200) {
//					var json = JSON.parse(req.responseText);
//					console.log(json.email + ", " + json.password)
//				}
//			}

			var data = JSON.stringify(user);

			req.send(data);
			
		}
		
		$scope.acceptNewUser = function(user)
		{
			user.showAccept=false;
			user.showDecline=false;
			user.showInserted=true;
			user.showDeclined=false;
			
			var req = new XMLHttpRequest();

			var url = encodeURI("services/newUsers");

			req.open("PUT",url,true);

			req.setRequestHeader("Content-type", "application/json");
			
			req.onreadystatechange = function()
			{
				if (req.readyState == 4) 
				{
					/*
					 * Edw to newUsers prepei na pairnei thn apanthsh ok h' oxi gia ton an mpeike o xrhsths sto systhma
					 */
//					alert(req.status);
				}
			}

//			req.onreadystatechange = function () { 
//				if (req.readyState == 4 && req.status == 200) {
//					var json = JSON.parse(req.responseText);
//					console.log(json.email + ", " + json.password)
//				}
//			}

			var data = JSON.stringify(user);

			req.send(data);
			
		}
		
	}])

})();
(function()
{
	var app = angular.module('menu',[]);
	
	deleteCookie = function()
	{
		setCookie("username",'',0);
		window.location = "https://localhost:8443/Project1WithGerryDB/BootWelcome.html";
	};
	
	app.directive('menuDirective',function()
	{
		return {
			restrict:'E',
			templateUrl:'menu.html',
		};
	});
	
	app.controller('MenuController',['$scope',function($scope){
		
		$scope.isLogedIn = function()
		{
			return getCookie("username") != '';
		}
		
	}]);
	
	app.controller('NewMessageController',['$scope','$http',function($scope,$http){
		
		$scope.newMessagesCounter = 0;
		
		$scope.getNewMessages = function()
		{
			username = getCookie("username");
			console.log("user = " + username);
			
			$http.post("services/messages/newMessages",username).success(function(data){
				
				$scope.newMessagesCounter = data;
				
			});
		}
		
	}]);
	
})();
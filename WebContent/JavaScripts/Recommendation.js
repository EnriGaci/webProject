(function(){
	
	var app = angular.module("RecApp",['menu']);
	
	app.controller('RecController',['$scope','$http',function($scope,$http){
		
		$scope.items = [];
		
		$scope.getRecommendations = function()
		{
			
			$http.get("services/users/recommendations/"+ getCookie('username') ).success(function(data){
				
				$scope.items = data;
				
				for (var i = 0; i < $scope.items.length; i++) {
				
					$scope.items[i].images = ['./pictures/yasuo_1.jpeg'];
				}
				
			});
			
		}
		
		
	}]);
	
	
	
})();
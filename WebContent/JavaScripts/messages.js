(function(){
	
	var app = angular.module('messages',['menu']);
	
	app.controller('PanelController',['$scope','$http',function($scope,$http)
	{
		$scope.tab = 0;
		
		$scope.newMessage = 
		{
			text:'',
			sender:getCookie("username"),
			reciever:'',
		}
		
		$scope.successfulSent = 0;
		$scope.senderNotExists = 0;
		$scope.serviceError = 0;
		
		$scope.sendMessage = function()
		{
			var message = 
			{
				text:'',
				sender:getCookie("username"),
				reciever:'',
			}
			
			/*
			 * An den valw ta stoixeia se neo json stelnetai apenanti keno
			 */
			message.text = $scope.newMessage.text;
			message.sender = $scope.newMessage.sender;
			message.reciever = $scope.newMessage.reciever;
			
			$http.post('services/messages',message).success(function(data){
				
				console.log("data=" + data);
				
				if( data == 0)
				{
					$scope.successfulSent = 1;
					$scope.senderNotExists = 0;
					$scope.newMessage.text = '';
					$scope.newMessage.reciever='';
				}
				if(data == -1)
				{
					$scope.senderNotExists = 1;
					$scope.successfulSent = 0;
				}
				if( data == -2 )
				{
					$scope.serviceError = 1;
					$scope.successfulSent = 0;
					$scope.senderNotExists = 0;
				}
				
			});
			
		}
		
		$scope.show = [];
		
		$scope.initShow = function(pos)
		{
			$scope.show[pos] = false;
		}
		
		$scope.changeMessageState= function(pos,message)
		{
			$scope.show[pos] = !$scope.show[pos];
			
			$http.post("services/messages/updateReadingState",message);
			
		}
		
		$scope.outbox = [];
		$scope.inbox = [];
		
		$scope.selectTab = function(tab)
		{
			$scope.tab = tab;
			if(tab === 1)
			{
				/*edw 8a fernw ta eiserxomena apo th vash */
				name = getCookie("username");
				$http.get("services/messages/get/"+name).success(function(data)
				{
					$scope.inbox = data;
				});
				
			}
			if(tab===2)
			{
				name = getCookie("username");
				$http.get("services/messages/getOutBox/"+name).success(function(data){
					$scope.outbox = data
				});
			}
		};
		
		$scope.isSelected = function(tab)
		{
			return $scope.tab === tab;
		};
		
		
	}]);
	
})();
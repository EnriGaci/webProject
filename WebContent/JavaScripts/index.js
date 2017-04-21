(function()
{
	var item = {
			id:'',
			name: 'Yauso Photo',
			price: 2.95,
			currently: 1.80,
			description: 'New awsome yasuo photo',
			images:[
			        "./pictures/yasuo_1.jpeg",
			        "./pictures/yasuo_1.jpeg",
			        "./pictures/yasuo_1.jpeg",
			        "./pictures/yasuo_2.jpeg",
			        ],
			categories:[
			            'Games',
			            ],
			locationName: 'Athens',
			latitude:37.988359,
			longitude:23.742901,
			firstBid:1.00,
			endsDate:0,
			started:0,
			seller:'',
			country:'Greece',
	
	}
	
	var app = angular.module('indexApp',['menu']);
	
	app.controller('IndexController',['$scope','$http','$log',function($scope,$http,$log)
	{
		$scope.$log = $log;
		$scope.showInserted = 0;
		$scope.option=0;
		$scope.showDiv = 0;
		
		$scope.item = item;
		
		$scope.username = '';
		
		$scope.checkedUser = 0;
		
		$scope.items = [];
		
		/* elegxei ean o xrhsths exei egkri8ei apo ton admin gia na emfanizei katallhla divs*/
		$scope.checkUser = function()
		{
			$scope.username = getCookie("username");
			
			if($scope.username!='')
			{
				var req = new XMLHttpRequest();

				var url = encodeURI("services/users/checkIfNewUser/"+$scope.username);
				
				req.open("GET",url,true);

				req.onreadystatechange = function () 
				{ 
					if (req.readyState == 4 && req.status == 302)
					{
						$scope.checkedUser = 1;
					}
				}
				
				req.send(null);
			}
			
		}
		
		/* elegxei ean exei kanei login o xrhsths */
		$scope.isLogedInUser = function()
		{
			if( $scope.username != '' && $scope.checkedUser === 1)
				return true;
			else
				return false;
		}
		
		$scope.auctionStarted = 0;
		
		/* synarthsh pu ksekinaei th dhmoprasia */
		$scope.startAuction = function(item)
		{
			$scope.item = item;
			
			var time = new Date();
			
			/* elegkse ean to end date einai egkyro */
			if( $scope.item.endsDate < time.getTime() )
			{
				$scope.warningStatus = 'smallEndDate';
				return;
			}

			$scope.insertToDb();
			
			$http.post("services/items/startAuction/"+$scope.item.id).success(function(data)
			{
				$scope.auctionStarted = 1;
				console.log(data);
			});
			
		}
		
		$scope.selectOption = function(option)
		{
			$scope.auctionStarted = 0;
			$scope.option = option;
			/*
			 * shmainei oti phga sto manage auctions
			 * kai xreiazomai th lista twn items pou exei dhmiourghsei o xrhsths
			 */
			if( option === 2)
			{
				$http.get('services/items/getItemsByUserName/'+$scope.username).success(function(data)
				{
					var time = new Date();
					$scope.items = [];
					for (var i = 0; i < data.length; i++) 
					{
						/*
						 * Emfanish sto xrhsth gia configuration mono ta items pou den exoun ginei started
						 * h' exoun ginei started  alla den exei ypovalei kapoios bid
						 */
						if( data[i].started == 0 || (data[i].started > 0 && data[i].bids.length == 0) )
							$scope.items.push(data[i]);
					}
					
				});
			}
			
		}
		
		$scope.isSelectedOption = function(option)
		{
			return $scope.option === option;
		}
		
		$scope.setDiv = function(divNumber)
		{
			$scope.showDiv = divNumber;
		}
		
		$scope.isSelectedDiv = function(divNumber)
		{
			return $scope.showDiv === divNumber;
		}
		
		$scope.warningStatus='';
		
		$scope.resetWarning = function()
		{
			$scope.warningStatus = '';
		}
		
		/*
		 * synarthsh pou eisagei to item sth vash
		 */
		$scope.insertToDb = function()
		{
			var d = new Date($scope.item.endsDate);
			
			$scope.item.endsDate = d.getTime();
			$scope.item.seller = getCookie("username");
			var req = new XMLHttpRequest();

			var url = encodeURI("services/items");

			req.open("POST",url,false);

			req.setRequestHeader("Content-type", "application/json");

			req.onreadystatechange = function () 
			{ 
				if (req.readyState == 4) 
				{
					if(req.responseText > 0)
					{
						console.log("To item mphke sth vash"+req.responseText);
						$scope.item.id = req.responseText;
					}
				}
			}
			
			var data = JSON.stringify($scope.item);

			req.send(data);
		}
		
	}]);
	
	app.controller('PanelController',['$scope',function($scope)
	{
		$scope.tab = 1;
		
		$scope.selectTab= function(setTab)
		{
			$scope.tab= setTab;
		}
		
		$scope.isSelected = function(checkTab)
		{
			return $scope.tab === checkTab;
		}
		
	}]);
	
	app.controller('WarningController',['$scope',function($scope)
	{
	    $scope.isSelectedWarning = function(warningName)
	    {
	    	return $scope.$parent.warningStatus === warningName;
	    }
	    
	}]);
	
	app.controller('CategoryController',['$scope',function($scope)
	{
		$scope.category = '';
		
		$scope.addCategory = function(item)
		{
			if( item.categories == null )
				item.categories = [$scope.category];
			else
				item.categories.push($scope.category);
			
			$scope.category = '';
		}
		
	}]);
	
	app.controller('UserAuctionedItems',['$scope','$http',function($scope,$http)
	{
		
		/* 
		 * ean 8elei o xrhsths na kanei configure ena item 
		 */
		$scope.configureAuction = function(item)
		{
			/* 8ese to item pros epksergasia tou div create auction 
			 * to item pou epelekse o xrhsths
			 */
			$scope.$parent.item = item;
			/* kai phgaine sto option 1 (dhladh create auction)*/
			$scope.$parent.selectOption(1);
		}
		
		/*
		 * Edw ginetai delete to item
		 */
		$scope.deleteAuction = function(item)
		{
			$http.delete('services/items/'+item.id).success(function(data)
			{
				if( data == 0 )
				{
					console.log("ola kala");
					var index = $scope.items.indexOf(item);
					if (index > -1) {
						$scope.items.splice(index, 1);
					}
				}
				else
					console.log("kati phge strava");
			});
		}
		
	}]);
	
	app.directive('itemSpecs',function(){
		
		return {
			
			/*
			 * A configuration object defining how your object will work
			 */
			restirct:'E',
			templateUrl: 'item-specs.html',
			
		};
		
	});
	
})();
























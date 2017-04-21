function initMap() 
		{
	        var mapDiv = document.getElementById('map');
	        var map = new google.maps.Map(mapDiv, {
	          center: {lat: 44.540, lng: -78.546},
	          zoom: 8
	        });
	    }

(function()
{
	
	var app = angular.module('auctionsApp',['menu']);
	
	app.controller('AuctionsController',['$scope','$http',function($scope,$http)
	{
		$scope.items = [];
		
		$scope.categoryChecked = 'all';
		$scope.categories = ['all'];
		
		$scope.locationChecked = 'all';
		$scope.locations = ['all'];
		
		$scope.partOfDescription = '';
		
		$scope.price = '';
		
		/*
		 * fernei ta antikeimena pros dhmoprasia apo to endpoint
		 * auta einai ta antikeimena pou exoun started > 0
		 */
		$scope.getItemsFromDb = function()
		{
			$http.get("services/items/auctions").success(function(data)
			{
				$scope.items = data;
				$scope.initLocation();
				$scope.initCategories();
			});
		}

		$scope.isSelectedLocation = function(location)
		{
			return $scope.locationChecked === location;
		}
		
		$scope.initLocation = function()
		{
			for (var int = 0; int < $scope.items.length; int++) {
				if( !($scope.locations.indexOf($scope.items[int].locationName) > -1) )
					$scope.locations.push($scope.items[int].locationName);
			}
		}
		
		$scope.initCategories = function()
		{
			for (var int = 0; int < $scope.items.length; int++) {
				item = $scope.items[int];
				for (var int2 = 0; int2 < item.categories.length; int2++) {
					if( !($scope.categories.indexOf(item.categories[int2]) > -1) )
						$scope.categories.push(item.categories[int2]);
				}
			}
		}
		
		$scope.selectLocation = function(location)
		{
			$scope.locationChecked = location;
		}
		
		$scope.isSelectedCategory = function(category)
		{
			return $scope.categoryChecked === category;
		}
		
		$scope.selectCategory = function(category)
		{
			$scope.categoryChecked = category;
		}
		
		$scope.hasCategory = function(item)
		{
			if( $scope.categoryChecked === 'all' )
			{
				item.show = 1;
				return ;
			}
			
			for (var int = 0; int < item.categories.length; int++) {
				if( item.categories[int] === $scope.categoryChecked )
				{
					item.show = 1;
					return ;
				}
			}
			
			item.show = 0;
			
			return ;
		}
		
		$scope.itemHasLocation = function(item)
		{
			if($scope.locationChecked === 'all' )
			{
				item.show = 1;
				return;
			}
			if ( item.locationName != $scope.locationChecked)
				item.show = 0;
			else
				item.show = 1;
		}
		
		
		$scope.showItem = function(item)
		{
			$scope.hasCategory(item);
			if( item.show === 0) return 0;
			$scope.itemHasSubDescription(item);
			if( item.show === 0) return 0;
			$scope.itemHasPrice(item);
			if( item.show === 0) return 0;
			$scope.itemHasLocation(item);
			return item.show === 1;
		}
		
		$scope.itemHasSubDescription = function(item)
		{
			if($scope.partOfDescription === '' )
			{
				item.show = 1;
				return;
			}
			if(item.description.indexOf($scope.partOfDescription) > -1)
				item.show = 1;
			else
				item.show = 0;
			
		}
		
		$scope.itemHasPrice = function(item)
		{
			if ($scope.price === '')
			{
				item.show =1;
				return;
			}
			
			if( item.price <= $scope.price)
				item.show =1;
			else
				item.show = 0;
		}
		
		$scope.initMap = function(item) 
		{
			if( item == null)
				myLatLng = {lat: $scope.items[0].latitude, lng: $scope.items[0].longtitude};
			else
				myLatLng = {lat: item.latitude, lng: item.longtitude};
			
			var mapDiv = document.getElementById('map');
	        var map = new google.maps.Map(mapDiv, {
	          center: myLatLng,
	          zoom: 11
	        });
	        
			for (var int = 0; int < $scope.items.length; int++) 
			{
				items = $scope.items;
				
				if( item == null )
				{
					curLatLng = {lat: items[int].latitude, lng: items[int].longtitude};
					title = items[int].name;
				}
				else
				{
					curLatLng = myLatLng;
					title = item.name;
				}

				var marker = new google.maps.Marker({
					position: curLatLng,
					map: map,
					title: title
				});
				
				if( item != null )
					return;
			}
	    }
		
		$scope.isLogedInUser = function()
		{
			return getCookie("username") != '';
		}
		
	}]);
	
	app.controller('PreviewController',['$scope',function($scope)
	{

		$scope.changeTab = function(tab,item)
		{
			tab = !tab
			return tab;
		}
		
	}]);
	
	app.controller('BidsController',['$scope','$http',function($scope,$http)
	{
		$scope.bid = null;
		$scope.notAnumber = 0;
		
		$scope.showInMap = 0;
		
		$scope.showBidVerificaton = 0;
		$scope.showLowBidError = 0;
		
		$scope.lowBidError = function()
		{
			return $scope.showLowBidError === 1;
		}
		
		$scope.setShowInMap = function(value)
		{
			$scope.showInMap = value;
		}
		
		$scope.isShowInMapSelected = function(value)
		{
			return $scope.showInMap === value;
		}
		
		$scope.isSelectedBidVerificaton = function()
		{
			return $scope.showBidVerificaton;
		}
		
		$scope.submitBid = function(item)
		{
			/*
			 * kwdikas gia na ginei to bid
			 * kai anoigma mynhmatwn metaksy bidder kai seller
			 */
			if( $scope.bid < item.currently)
			{	
				$scope.showLowBidError = 1;
				return;
			}
			else
			{
				$scope.showLowBidError = 0;
				/*
				 * Paw na apo8hkeusw to bid sth vash
				 */
				var username = getCookie("username");
				console.log("value = " + $scope.bid);
				item.currently = $scope.bid;
				$http.get("services/items/placeBid/"+$scope.bid+"/"+item.id+"/"+username).success(function(data)
				{
					
				});
			}
				
			$scope.showBidVerificaton = 0;
		}
		
		$scope.cancelBid = function()
		{
			$scope.bid = null;
			$scope.showBidVerificaton = 0;
		}
		
		$scope.verifyBid = function()
		{
			if( $scope.notANumber() )
				return;
			$scope.showBidVerificaton = 1;
		}
		
		$scope.isNumeric = function()
		{
			if(isNaN($scope.bid) || ($scope.bid % 1 != 0) )
			{
				$scope.notAnumber = 1;
			}
			else
				$scope.notAnumber = 0;
		}
		
		$scope.notANumber = function()
		{
			return $scope.notAnumber === 1;
		}
		
	}]);
	
})();
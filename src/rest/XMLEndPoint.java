package rest;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.websocket.server.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import JAXBModel.Bid;
import JAXBModel.Bidder;
import JAXBModel.Item;
import JAXBModel.Location;
import JAXBModel.Seller;
import db.BidDAOImpl;
import db.CategoryDAOImpl;
import db.ItemDAOImpl;
import db.UserDAOImpl;

import utilities.Recommentation;

@Path("/xml")
public class XMLEndPoint {
	
	@GET
	@Path("/generateRecs")
	public void generateRecs() throws JAXBException, FileNotFoundException
	{
		Recommentation rec = new Recommentation();
		
		double[][] UsersVectors = rec.GenerateUsersVectors(); //Make Users Vectors with Amounts

		int K = 50;
		List<ArrayList<Integer>> neighbors = rec.KNN(UsersVectors,K); //find neighbors with KNN ( K=5 )

//		rec.FindRecommendedItems(neighbors,UsersVectors); //Find Users Recommended items
	}
	
	@GET
	@Path("/read")
	public pojo.Items readXml() throws JAXBException, FileNotFoundException
	{
		
		JAXBContext context = JAXBContext.newInstance(pojo.Items.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		
		for( int j = 7; j <13 ; j++){
			pojo.Items items = (pojo.Items)unmarshaller.unmarshal(new FileReader("/home/henry/workspace/Ted/JaxbExample/src/ebay-data/items-"+j+".xml"));

			ItemDAOImpl itemd = new ItemDAOImpl();
			BidDAOImpl bidd = new BidDAOImpl();
			UserDAOImpl userd = new UserDAOImpl();
			CategoryDAOImpl categoryd = new CategoryDAOImpl();

			entities.Item iteme = new entities.Item();

			for (pojo.Item itemp : items.getItems()) {

				iteme.setName(itemp.getName());

				iteme.setCurrently(itemp.getCurrently());
				iteme.setFirst_Bid(itemp.getFirst_Bid());
				iteme.setNumber_of_Bids(itemp.getNumber_of_Bids());

				List<entities.Bid> bids = new ArrayList<entities.Bid>();

				SimpleDateFormat formatter = new SimpleDateFormat("MMM-dd-yy hh:mm:ss"); // Month.Day.Year
				Date d = new Date();
				long timestamp ;
				
				Long itemId = itemd.insertItem(iteme);
				
				iteme = itemd.getItemById(itemId);
				
				for (pojo.Bid bid : itemp.getBids().getBids()) {

					entities.Bid bide = new entities.Bid();

					bide.setAmount(bid.getAmmount());

					try {
						d = formatter.parse(bid.getTime());
					}
					catch (Exception e) {
						//The handling for the code
					}

					timestamp = d.getTime();
					bide.setTime(timestamp);
					bide.setItem(iteme);

					entities.User usere;

					usere = userd.getByUserName(bid.getBidder().getUserID());

					// an den yparxei o bidder sth vash apo8hkeuse ton
					if(usere == null){
						usere = new entities.User();
						usere.setUsername(bid.getBidder().getUserID());
						usere.setRating(bid.getBidder().getRating());
						usere.setLocation(bid.getBidder().getLocation());
						usere.setCountry(bid.getBidder().getCountry());
						userd.insertUser(usere);  // vale ton user sth vash
					}

					bide.setUser(usere);

					Long success = bidd.insert(bide);

					if( success < 0 )
						System.out.println("-->to bid den mphke sth vash");

					bids.add(bide);

				}

				iteme.setLocation(itemp.getLocation().getValue());
				if(itemp.getLocation().getLongitude() != null)
					iteme.setLongitude(itemp.getLocation().getLongitude());
				else
					iteme.setLongitude(0.0);
				if(itemp.getLocation().getLatitude() != null)
					iteme.setLatitude(itemp.getLocation().getLatitude());
				else
					iteme.setLatitude(0.0);
				iteme.setCountry(itemp.getCountry());

				try {
					d = formatter.parse(itemp.getStarted());
				}
				catch (Exception e) {
					//The handling for the code
				}

				timestamp = d.getTime();
				iteme.setStarted(timestamp);

				try {
					d = formatter.parse(itemp.getEnds());
				}
				catch (Exception e) {
					//The handling for the code
				}

				timestamp = d.getTime();
				iteme.setEnds(timestamp);

				entities.User usere;

				usere = userd.getByUserName(itemp.getSeller().getUserID());

				if(usere == null){
					usere = new entities.User();
					usere.setRating(itemp.getSeller().getRating());
					usere.setUsername(itemp.getSeller().getUserID());
					userd.insertUser(usere);
				}
				
				iteme.setUser(usere);
				iteme.setItemsHasCategories(null);

				itemId = itemd.insertItem(iteme);

				entities.Item insertedItem = itemd.getItemById(itemId);

				List<entities.ItemsHasCategory> itemECategories = new ArrayList<entities.ItemsHasCategory>();

				for (String category : itemp.getCategories()) {

					entities.ItemsHasCategory ihc = new entities.ItemsHasCategory();

					entities.Category cat = categoryd.getCategoryByName(category);

					// ean den yparxei to category sth vash vale to
					if( cat == null ){
						cat = new entities.Category();
						cat.setName(category);
						categoryd.insertCategory(cat);
					}

					ihc.setCategory(cat);
					ihc.setItem(insertedItem);
					itemECategories.add(ihc);

				}

				insertedItem.setItemsHasCategories(itemECategories);

				Long ok2 = itemd.updateItemCategories(itemECategories,insertedItem.getItemId());

				itemd.insertItem(iteme);

			} // telos for gia items sto item-x.xml
		} //telos for gia ola ta arxeia
			
		return null;
	}
	
	@GET
	@Produces("application/xml")
	public List<Item> generateXML()
	{
		List<Item> items = new ArrayList<Item>();
		ItemDAOImpl itemDao = new ItemDAOImpl();
		BidDAOImpl bidsDao = new BidDAOImpl();
		
		List<entities.Item> entitiesItems = itemDao.getItems();
		
		int i = 0;
		for (entities.Item itemd : entitiesItems) 
		{
			if( i > 100) break;
			
			Item item = new Item();
			
			item.setName(itemd.getName());
			item.setId(itemd.getItemId());
			List<String> categories = new ArrayList<String>();
			for (entities.ItemsHasCategory category : itemd.getItemsHasCategories() ) 
			{
				categories.add(category.getCategory().getName());
			}
			item.setCategories(categories);
			item.setCurrently(itemd.getCurrently());
			item.setFirstBid(itemd.getFirst_Bid());
			item.setNumberOfBids(itemd.getNumber_of_Bids());
			if(itemd.getStarted() != null )
			{
				Date started = new Date(itemd.getStarted());
				item.setStarted(started.toString());
			}
			else
				item.setStarted("");
			if(itemd.getEnds() != null )
			{
				Date ends = new Date(itemd.getEnds());
				item.setEnds(ends.toString());
			}
			else
				item.setEnds("");
			
			List<entities.Bid> bids = bidsDao.findByItemId(itemd.getItemId());
			
			if(bids != null)
			{
				
				List<Bid> jaxBids = new ArrayList<Bid>();

				for (entities.Bid bid : bids) {

					Bid jaxBid = new Bid();

					Bidder bidder = new Bidder();
					bidder.setCountry(bid.getUser().getCountry());
					bidder.setLocation(bid.getUser().getLocation());
					bidder.setRating(bid.getUser().getRating());
					bidder.setUsername(bid.getUser().getUsername());

					jaxBid.setBidder(bidder);
					jaxBid.setAmmount(bid.getAmount());
					Date time = new Date(bid.getTime());
					jaxBid.setTime(time.toString());

					jaxBids.add(jaxBid);

				}

				item.setBids(jaxBids);
			}
			else
				item.setBids(null);
			
			Location location = new Location();
			location.setLatitude(itemd.getLatitude());
			location.setLongitude(itemd.getLongitude());
			location.setName(itemd.getLocation());
			
			item.setLocation(location);
			
			Seller seller = new Seller();
			
			if(itemd.getUser() != null)
				seller.setRating(itemd.getUser().getRating());
			else
				seller.setRating(null);
			
			if(itemd.getUser()!=null)
				seller.setUsername(itemd.getUser().getUsername());
			else
				seller.setUsername(null);															
			
			item.setSeller(seller);
			item.setDescription(itemd.getDescription());
			item.setCountry(itemd.getCountry());
			
			items.add(item);
			
			i++;
		}
		
		
		return items;
	}

}

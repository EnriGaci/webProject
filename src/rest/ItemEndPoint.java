package rest;

import java.util.List;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.Response.Status;

import db.BidDAOImpl;
import db.CategoryDAOImpl;
import db.ImageDAOImpl;
import db.ItemDAOImpl;
import db.ItemsHasCategoryDAOImpl;
import db.UserDAOImpl;
import model.Bid;
import model.Item;

@Path("/items")
public class ItemEndPoint {
	
	@GET
	@Path("/getItemsByUserName/{username:[a-zA-Z_0-9][a-zA-Z_0-9]*}")
	@Produces({"application/json"})
	public List<Item> getUserItems(@PathParam("username")final String username) 
	{
		
		UserDAOImpl userDao = new UserDAOImpl();
		
		entities.User userd = userDao.getByUserName(username);
		
		ItemDAOImpl itemDao = new ItemDAOImpl();
		
		List<entities.Item> items = itemDao.getUserItems(userd);
		List<Item> modelItems = new ArrayList<Item>();
		
		for (entities.Item item : items) 
		{
			Item itemd = new Item();
			
			itemd.setPrice(item.getBuyPrice());
			itemd.setDescription(item.getDescription());
			itemd.setCurrently(item.getCurrently());
			itemd.setEndsDate(item.getEnds());
			itemd.setFirstBid(item.getFirst_Bid());
			itemd.setLatitude(item.getLatitude());
			itemd.setLocationName(item.getLocation());
			itemd.setLongitude(item.getLongitude());
			itemd.setName(item.getName());
			itemd.setNumber_of_Bids(item.getNumber_of_Bids());
			itemd.setStarted(item.getStarted());
			itemd.setSeller(userd.getUsername());
			itemd.setCountry(item.getCountry());
			List<String> images = new ArrayList<String>();
			for (entities.Image img : item.getImages()) 
			{
				images.add(img.getName());
			}
			itemd.setImages(images);
			List<Bid> bids = new ArrayList<Bid>();
			for (entities.Bid b : item.getBids()) 
			{
				Bid bid = new Bid();
				bid.setAmount(b.getAmount());
				bid.setBidderId(b.getUser().getUserId());
				bid.setTime(b.getTime());
				bids.add(bid);
			}
			itemd.setBids(bids);
			itemd.setId(item.getItemId());
			ItemsHasCategoryDAOImpl icDao = new ItemsHasCategoryDAOImpl();
			
			List<String> categories = icDao.getItemCategories(item.getItemId());
			itemd.setCategories(categories);
			
			modelItems.add(itemd);
		}
		
		return modelItems;
	}
	
	@GET
	@Path("/auctions")
	@Produces({"application/json"})
	public List<Item> getAuctions() 
	{
		
		ItemDAOImpl itemDao = new ItemDAOImpl();
		
		List<entities.Item> items = itemDao.getItems();
		List<Item> modelItems = new ArrayList<Item>();
		
		for (entities.Item item : items) 
		{
			/*
			 * ean exei liksei h dhmoprasia h' den exei ksekinhsei mhn tis valeis sth lista
			 */
			if( item.getStarted() == null )
				continue;
			
			if( item.getStarted() == 0 || item.getEnds() < System.currentTimeMillis() )
				continue;
			
			Item itemd = new Item();
			
			itemd.setPrice(item.getBuyPrice());
			itemd.setDescription(item.getDescription());
			itemd.setCurrently(item.getCurrently());
			itemd.setEndsDate(item.getEnds());
			itemd.setFirstBid(item.getFirst_Bid());
			itemd.setLatitude(item.getLatitude());
			itemd.setLocationName(item.getLocation());
			itemd.setLongitude(item.getLongitude());
			itemd.setName(item.getName());
			itemd.setNumber_of_Bids(item.getNumber_of_Bids());
			itemd.setStarted(item.getStarted());
			itemd.setSeller(item.getUser().getName());
			itemd.setCountry(item.getCountry());
			List<String> images = new ArrayList<String>();
			for (entities.Image img : item.getImages()) 
			{
				images.add(img.getName());
			}
			itemd.setImages(images);
			List<Bid> bids = new ArrayList<Bid>();
			for (entities.Bid b : item.getBids()) 
			{
				Bid bid = new Bid();
				bid.setAmount(b.getAmount());
				bid.setBidderId(b.getUser().getUserId());
				bid.setTime(b.getTime());
				bids.add(bid);
			}
			itemd.setBids(bids);
			itemd.setId(item.getItemId());
			
			ItemsHasCategoryDAOImpl icDao = new ItemsHasCategoryDAOImpl();
			
			List<String> categories = icDao.getItemCategories(item.getItemId());
			itemd.setCategories(categories);
			
			modelItems.add(itemd);
		}
		
		return modelItems;
	}
	
	@DELETE
	@Consumes({ "application/json" })
	@Path("/{id:[0-9][0-9]*}")
	public Integer delete(@PathParam("id") Long itemId) {
		
		ItemDAOImpl dao = new ItemDAOImpl();
		
		int success = dao.deleteById(itemId);
		
		if( success == 0)
			return 0;
		else
			return -1;
	}
	
	@GET
	@Consumes("application/json")
	@Path("/placeBid/{ammount}/{itemId:[0-9][0-9]*}/{bidderName}")
	public int placeBid(@PathParam("ammount") final String bidAmmount, @PathParam("itemId") final Long itemId,
			@PathParam("bidderName") final String bidderName){
		
		ItemDAOImpl dao = new ItemDAOImpl();
		entities.Item item = dao.getItemById(itemId);
		
		item.setCurrently(bidAmmount);
		item.setNumber_of_Bids(item.getNumber_of_Bids()+1);
		
		BidDAOImpl bidDao = new BidDAOImpl();
		
		/* Elegkse ean o xrhsths exei ksanakanei bid se auto to item kai an nai ananewse to bid tou */
		entities.Bid bid = bidDao.findByBidderAndItem(bidderName, itemId);
		
		if( bid == null)
		{
			bid = new entities.Bid();
			bid.setAmount(bidAmmount);
			bid.setTime(System.currentTimeMillis());
			bid.setItem(item);
			UserDAOImpl userDao = new UserDAOImpl();
			entities.User user = userDao.getByUserName(bidderName);
			bid.setUser(user);
			
			Long bidId = bidDao.insert(bid);
			
			if( bidId == -1)
				return -2;
		}
		else
		{
			int error = bidDao.update(bid.getBidId(), bidAmmount);
			if( error != 0)
				return -3;
		}
		
		item.addBid(bid);
		
		Long success = dao.updateItem(item);
		
		if( success != 0 )
			return -1;
		
		return 0;
	
	}
	
	@POST
	@Consumes({ "application/json" })
	@Path("/startAuction/{id:[0-9][0-9]*}")
	public int startAuction(@PathParam("id") final Long itemId) {
		
		ItemDAOImpl dao = new ItemDAOImpl();
		
		int success = dao.updateStarted(itemId);
		
		return success;
	
	}
	
	@POST
	@Produces({ "application/json" })
	@Path("/getItem/{id:[0-9][0-9]*}")
	public Item findItemById(@PathParam("id") final Long itemId) {
		
		if( itemId==null )
			return null;
		
		ItemDAOImpl dao = new ItemDAOImpl();
		
		entities.Item item = dao.getItemById(itemId);
		
		if(item == null)
			return null;
		
		Item itemd = new Item();
		
		itemd.setBids(null);
		itemd.setPrice(item.getBuyPrice());
		itemd.setDescription(item.getDescription());
		itemd.setCurrently(item.getCurrently());
		itemd.setEndsDate(item.getEnds());
		itemd.setFirstBid(item.getFirst_Bid());
		itemd.setLatitude(item.getLatitude());
		itemd.setLocationName(item.getLocation());
		itemd.setLongitude(item.getLongitude());
		itemd.setName(item.getName());
		itemd.setNumber_of_Bids(item.getNumber_of_Bids());
		itemd.setStarted(item.getStarted());
		itemd.setImages(null);
		itemd.setSeller(item.getUser().getName());
		
		return itemd;
	}
	
	@POST
	@Consumes({ "application/json" })
	public Long create(final Item item) {
		
		ItemDAOImpl dao = new ItemDAOImpl();
		UserDAOImpl userDao = new UserDAOImpl();
		
		entities.Item itemd;
		
		// auth h if einai gia otan kanei configure o xrhsth ena item tou
		if(item.getId() != null )
		{
			delete(item.getId());
		}
		itemd = new entities.Item();
		
		entities.User seller = userDao.getByUserName(item.getSeller());
		
		itemd.setBuyPrice(item.getPrice());
		itemd.setDescription(item.getDescription());
		itemd.setCurrently(item.getCurrently());
		itemd.setEnds(item.getEndsDate());
		itemd.setFirst_Bid(item.getFirstBid());
		itemd.setLatitude(item.getLatitude());
		itemd.setLocation(item.getLocationName());
		itemd.setLongitude(item.getLongitude());
		itemd.setName(item.getName());
		itemd.setNumber_of_Bids(0);
		itemd.setStarted(new Long(0));
		itemd.setImages(null);
		itemd.setImages(null);
		itemd.setUser(seller);
		itemd.setBids(null);
		itemd.setCountry(item.getCountry());
		
		Long itemdId;
		
		itemdId = dao.insertItem(itemd);
		
		/* ean gia kapoio logo den dhmiourgh8hke to item */
		if(itemdId == -1)
			return new Long(-1);
		
		entities.Item insertedItem;
		insertedItem = dao.getItemById(itemdId);
		
		/*edw mhn ksexasw na valw ta images*/
		List<entities.Image> images = setImages(item.getImages(),insertedItem);
		insertedItem.setImages(images);
		
		/* prepei na valw ta categories toy item */
		List<entities.ItemsHasCategory> categories = setCategories(item.getCategories(),insertedItem);
		insertedItem.setItemsHasCategories(categories);
		
		Long success = dao.updateItem(insertedItem);
		
		if( success >= 0 )
			return insertedItem.getItemId();
		else
			return new Long(-2);

	}
	
	private List<entities.Image> setImages(List<String> modelItemImages,entities.Item insertedItem)
	{
		List<entities.Image> images = new ArrayList<entities.Image>();
		ImageDAOImpl dao = new ImageDAOImpl();
		for (String imagePath : modelItemImages) 
		{
			entities.Image image = new entities.Image();
			image.setName(imagePath);
			image.setItem(insertedItem);
			dao.insertImage(image);
			images.add(image);
		}
		return images;
	}
	
	private List<entities.ItemsHasCategory> setCategories(List<String> modelItemCategories,entities.Item insertedItem)
	{
		List<entities.ItemsHasCategory> categories = new ArrayList<entities.ItemsHasCategory>();
		CategoryDAOImpl categoryDao = new CategoryDAOImpl();
		for (String categoryName : modelItemCategories) 
		{
			entities.ItemsHasCategory itemHasCategory = new entities.ItemsHasCategory();
			entities.Category category;
			/* ean den yparxei to category me auto to onoma sth vash
			 * dhmiourghse to
			 */
			category = categoryDao.getCategoryByName(categoryName);
			if( category == null)
			{
				category = new entities.Category();
				category.setName(categoryName);
				categoryDao.insertCategory(category);
			}
			
			itemHasCategory.setItem(insertedItem);
			itemHasCategory.setCategory(category);
			categories.add(itemHasCategory);
			
		}
		return categories;
	}

}

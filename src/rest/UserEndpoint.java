package rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import db.ItemsHasCategoryDAOImpl;
import db.RecommendedItemDAOImpl;
import db.UserDAOImpl;
import model.Bid;
import model.Item;
import model.User;

@Path("/users")
public class UserEndpoint {
	
	@GET
	@Path("/recommendations/{username:[a-zA-Z_0-9][a-zA-Z_0-9]*}")
	@Produces({"application/json"})
	public List<Item> getRecommendations(@PathParam("username") final String username)
	{
		UserDAOImpl userd = new UserDAOImpl();
		entities.User user = userd.getByUserName(username);
		RecommendedItemDAOImpl recd = new RecommendedItemDAOImpl();
		
		List<entities.Item> recItems = recd.findRecommendations(user.getUserId());
		
		List<Item> modelItems = new ArrayList<Item>();
		
		for (entities.Item item : recItems) 
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
			itemd.setSeller(item.getUser().getUsername());
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

	@POST
	@Consumes({ "application/json" })
	public Response create(final User user) {
		
		entities.User userd = new entities.User();
		
		userd.setAddress(user.getAddress());
		userd.setEmail(user.getEmail());
		userd.setName(user.getName());
		userd.setPassword(user.getPassword());
		userd.setPhoneNumber(user.getPhoneNumber());
		userd.setSurname(user.getSurname());
		userd.setUsername(user.getUsername());
		userd.setAfm(user.getAfm());
		userd.setCheckUser(0);
		userd.setCountry(user.getCountry());
		userd.setLongitudeUser(user.getLongtitude());
		userd.setLatitudeUser(user.getLatitude());
		userd.setBids(null);
		userd.setItems(null);
		userd.setMessages1(null);
		userd.setMessages2(null);
		userd.setLocation(user.getLocation());
		
		UserDAOImpl userDao = new UserDAOImpl();
		Long id = userDao.insertUser(userd);
		return Response.created(
				UriBuilder.fromResource(UserEndpoint.class)
						.path(String.valueOf(id)).build()).build();
	}
	
	@POST
	@Path("/logIn")
	@Consumes({ "application/json" })
	public Response logIn(final User user) {
		
		System.out.println(" u = " + user.getUsername() + " p = " + user.getPassword());
		
		if( user.getUsername().equals("admin") && user.getPassword().equals("123" ))
			return Response.status(Status.ACCEPTED).build();
		
		UserDAOImpl userDao = new UserDAOImpl();
		
		entities.User resposeUser = userDao.CheckUserForSignIn(user.getUsername(), user.getPassword());
		
		if(resposeUser == null)
		{
			return Response.status(Status.NOT_FOUND).build();
		}
		else
			if(resposeUser.getCheckUser() == 0)
				return Response.status(Status.FORBIDDEN).build();
		
		if( !resposeUser.getPassword().equals(user.getPassword()) )
		{
			return Response.status(Status.BAD_REQUEST).build();
		}
		
		
		return Response.status(Status.FOUND).entity("Entity found for ").build();
		
	}

	@GET
	@Path("/{username:[a-zA-Z_0-9][a-zA-Z_0-9]*}")
	@Produces({"application/json"})
	public Response findByUserName(@PathParam("username")final String username) {
		
		UserDAOImpl userDao = new UserDAOImpl();
		
		entities.User userd = userDao.getByUserName(username);
		
		if (userd == null) 
		{
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.status(Status.FOUND).build();
	}
	
	@GET
	@Path("/checkIfNewUser/{username:[a-zA-Z_0-9][a-zA-Z_0-9]*}")
	public Response checkIfNewUser(@PathParam("username")final String username) {
		
		UserDAOImpl userDao = new UserDAOImpl();
		
		entities.User userd = userDao.getByUserName(username);
		
		if (userd == null) 
		{
			return Response.status(Status.NOT_FOUND).build();
		}
		
		if( userd.getCheckUser() == 0 )
		{
			return Response.status(Status.NOT_FOUND).build();
		}
		
		return Response.status(Status.FOUND).build();
	}

}

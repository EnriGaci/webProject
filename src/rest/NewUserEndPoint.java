package rest;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PostUpdate;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.Response.Status;

import db.UserDAOImpl;
import model.User;

@Path("/newUsers")
public class NewUserEndPoint {
	
	/*
	 * Kanei delete to new user pou aporiptei o admin
	 */
	@DELETE
	@Consumes({ "application/json" })
	public Response delete(final User user) {
		
		UserDAOImpl dao = new UserDAOImpl();
		Integer success = dao.deleteUser(user.getUsername());
		
		if(success == 1)
			return Response.status(Status.OK).build();
		else
			return Response.status(Status.EXPECTATION_FAILED).build();

	}
	
	/*
	 * dinei dikaiwmata sto neo xrhsth na xrhsimopoiei tis dhmoprasies
	 */
	@PUT
	@Consumes({ "application/json" })
	public Response acceptUser(final User user)
	{
		UserDAOImpl dao = new UserDAOImpl();
		Integer success = dao.updateUser(user.getUsername(),"checkUser","1");
		
		if( success == 0	)
			return Response.status(Status.NOT_MODIFIED).build();
		else
			return Response.status(Status.OK).build();
	}
	
	/*
	 * Epistrefei mia lista me tous users pou einai neoi kai 
	 * den tous exei dex8ei akoma o admin. Autoi anagnwrizontai
	 * apo to pedio checkUser to opoio einai 0 se periptwsh pou
	 * prokeitai gia neo xrhsth
	 */
	@GET
	@Produces({ "application/json" })
	@Path("/findNewUsers")
	public List<User> getNewUsers() {
		
		UserDAOImpl dao = new UserDAOImpl();
		
		List<entities.User> users = dao.getNewUsers((byte)0);
		List<User> modelUsers = new ArrayList<User>();
		
		for (entities.User user : users) 
		{
			User userd = new User();
			
			userd.setAddress(user.getAddress());
			userd.setEmail(user.getEmail());
			userd.setName(user.getName());
			userd.setPassword(user.getPassword());
			userd.setPhoneNumber(user.getPhoneNumber());
			userd.setSurname(user.getSurname());
			userd.setUsername(user.getUsername());
			userd.setAfm(user.getAfm());
			userd.setCountry(user.getCountry());
			userd.setLongtitude(user.getLongitudeUser());
			userd.setLatitude(user.getLatitudeUser());
			userd.setLocation(user.getLocation());
			
			modelUsers.add(userd);
		}
		
		return modelUsers;
		
	}

}

package rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import db.MessageDAOImpl;
import db.UserDAOImpl;

import javax.ws.rs.core.Response.Status;

import model.InboxMessage;
import model.NewMessage;
import model.OutboxMessage;

@Path("/messages")
public class MessageEndPoint {
	
	@POST
	@Path("/updateReadingState")
	@Consumes({ "application/json" })
	public int upadteMessageState(final InboxMessage message) 
	{
		MessageDAOImpl messaged = new MessageDAOImpl();
		
		Long success = messaged.updateReadState(message.getId());
		
		if( success < 0 )
			return -1;
		
		return 0;
	}
	
	@POST
	@Path("/newMessages")
	@Consumes({ "application/json" })
	public int getNewMessagesCounter(final String username) {
		
		MessageDAOImpl messaged = new MessageDAOImpl();
		
		List<entities.Message> messages = messaged.getInbox(username);
		
		int newMessagesCounter = 0;
		
		for (entities.Message message : messages) {
			if( message.getHasReadMessage() == 0 )
				newMessagesCounter++;
		}
		
		return newMessagesCounter;
	}
	
	@POST
	@Produces({"application/json"})
	public int create(final NewMessage message)
	{
		UserDAOImpl userDao = new UserDAOImpl();
		/*
		 * Elegxkse an yparxei o reciever
		 */
		entities.User reciever = userDao.getByUserName(message.getReciever());
		
		if( reciever == null )
			return -1;
		
		entities.User sender = userDao.getByUserName( message.getSender() );
		
		entities.Message messaged = new entities.Message();
		
		messaged.setMessage(message.getText());
		messaged.setUser1(sender);
		messaged.setUser2(reciever);
		messaged.setHasReadMessage(0);
		
		MessageDAOImpl messageDao = new MessageDAOImpl();
		Long success = messageDao.insert(messaged);
		
		if( success < 0 )
			return -2;
		
		return 0;
	}
	
	@GET
	@Path("/get/{userName}")
	@Produces({"application/json"})
	public List<InboxMessage> getIndox(@PathParam("userName") final String username)
	{
		
		MessageDAOImpl messageDao = new MessageDAOImpl();
		
		List<entities.Message> messagesd = messageDao.getInbox(username);
		List<InboxMessage> inbox = new ArrayList<InboxMessage>();
		
		
		if(messagesd != null)
		{
			for (entities.Message messaged : messagesd) {
				
				InboxMessage message = new InboxMessage();
				
				message.setSender(messaged.getUser1().getUsername());
				message.setText(messaged.getMessage());
				message.setId(messaged.getMessageId());
				
				inbox.add(message);
			}
			return inbox;
		}
		
		return null;
	}
	
	@GET
	@Path("/getOutBox/{userName}")
	@Produces({"application/json"})
	public List<OutboxMessage> getOutdox(@PathParam("userName") final String username)
	{
		
		MessageDAOImpl messageDao = new MessageDAOImpl();
		
		List<entities.Message> messagesd = messageDao.getOutbox(username);
		List<OutboxMessage> outbox = new ArrayList<OutboxMessage>();
		
		
		if(messagesd != null)
		{
			for (entities.Message messaged : messagesd) {
				
				OutboxMessage message = new OutboxMessage();
				
				message.setReciever(messaged.getUser2().getUsername());
				message.setText(messaged.getMessage());
				
				outbox.add(message);
			}
			return outbox;
		}
		
		return null;
	}

}

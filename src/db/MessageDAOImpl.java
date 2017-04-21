package db;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import entities.Message;

public class MessageDAOImpl {
	
	public Long updateReadState(Long messageId) 
    {
		EntityManager em = JPAResource.factory.createEntityManager();
	    EntityTransaction tx = em.getTransaction();
	    
        tx.begin();
        
        Message message = em.find(Message.class, messageId);
        message.setHasReadMessage(1);
        
        try 
        {
            tx.commit();
            return message.getMessageId();
        }
        catch (PersistenceException e)
        {
            if (tx.isActive()) tx.rollback();
            return new Long(-1);
        }
        finally 
        {
            em.close();
        }
    }
	
	public Long insert(Message message) 
    {
		EntityManager em = JPAResource.factory.createEntityManager();
	    EntityTransaction tx = em.getTransaction();
	    
        tx.begin();
        try 
        {
            em.persist(message);
            em.flush();
            tx.commit();
            return message.getMessageId();
        }
        catch (PersistenceException e)
        {
            if (tx.isActive()) tx.rollback();
            return new Long(-1);
        }
        finally 
        {
            em.close();
        }
    }
	
	public List<Message> getInbox(String username)
	{
		EntityManager em = JPAResource.factory.createEntityManager();
	    EntityTransaction tx = em.getTransaction();
	    
        tx.begin();
        
        String query = "Select i FROM Message i where i.user2.username LIKE :usern ";
        Query q = em.createQuery(query);
        q.setParameter("usern", username);
        
        List<Message> messages = q.getResultList();
        
        try 
        {
            tx.commit();
            return messages;
        }
        catch (PersistenceException e)
        {
            if (tx.isActive()) tx.rollback();
            return null;
        }
        finally 
        {
            em.close();
        }
	}
	
	public List<Message> getOutbox(String username)
	{
		EntityManager em = JPAResource.factory.createEntityManager();
	    EntityTransaction tx = em.getTransaction();
	    
        tx.begin();
        
        String query = "Select i FROM Message i where i.user1.username LIKE :usern ";
        Query q = em.createQuery(query);
        q.setParameter("usern", username);
        
        List<Message> messages = q.getResultList();
        
        try 
        {
            tx.commit();
            return messages;
        }
        catch (PersistenceException e)
        {
            if (tx.isActive()) tx.rollback();
            return null;
        }
        finally 
        {
            em.close();
        }
	}

}

package db;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import entities.Item;
import entities.User;


public class UserDAOImpl {
	
	public Long insertUser(User user) 
    {
		EntityManager em = JPAResource.factory.createEntityManager();
	    EntityTransaction tx = em.getTransaction();
	    
        tx.begin();
        try 
        {
            em.persist(user);
            em.flush();
            tx.commit();
            return user.getUserId();
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
	
	public Integer deleteUser(String username)
	{
		EntityManager em = JPAResource.factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        String query = "DELETE FROM User WHERE username= :usrnm";
        Query q = em.createQuery(query);
        q.setParameter("usrnm", username).executeUpdate();
        
        
        try 
        {
            tx.commit();
            return 1;
        }
        catch (PersistenceException e)
        {
            if (tx.isActive()) tx.rollback();
            return 0;
        }
        finally 
        {
            em.close();
        }
	}
	
    public User CheckUserForSignIn(String username, String password)
    {
    	EntityManager em = JPAResource.factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        String query = "SELECT u FROM User u WHERE u.username= :usrnm and u.password= :pass";
        Query q = em.createQuery(query);
        q.setParameter("usrnm", username);
        q.setParameter("pass", password);
        
        List<User> users = q.getResultList();
        
        try 
        {
            tx.commit();
            if (users != null && users.size() == 1)
            {
                return users.get(0);
            }
            return null;
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
    
    public Integer updateUser(String username, String field, String value)
    {
    	 EntityManager em = JPAResource.factory.createEntityManager();
         EntityTransaction tx = em.getTransaction();
         tx.begin();
         
         String query = "UPDATE User SET "+ field+"="+value+" WHERE username = :usrn";
         Query q = em.createQuery(query);
         q.setParameter("usrn", username).executeUpdate();
         
         try 
         {
             tx.commit();
             return 1;
         }
         catch (PersistenceException e)
         {
             if (tx.isActive()) tx.rollback();
             return 0;
         }
         finally 
         {
             em.close();
         }
    }
    
    public User getByUserName(String username)
    {
    	List<User> users = null;
		EntityManager em = JPAResource.factory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		tx.begin();

		String query = "select u from User u where u.username = :userName";
		Query q = em.createQuery(query);
		q.setParameter("userName", username);
		users =  q.getResultList();
		
		try 
        {
            tx.commit();
            if( !users.isEmpty() )
            	return users.get(0);
            else
            	return null;
        }
        catch (PersistenceException e)
        {
            if (tx.isActive()) 
            	tx.rollback();
            return null;
        }
        finally 
        {
            em.close();
        }
    	
    }
    
    public List<User> getUsers()
	{
		List<User> users = null;
		EntityManager em = JPAResource.factory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		tx.begin();

		Query q = em.createNamedQuery("User.findAll");
		users =  q.getResultList();
		
		try 
        {
            tx.commit();
            return users;
        }
        catch (PersistenceException e)
        {
            if (tx.isActive()) 
            	tx.rollback();
            return null;
        }
        finally 
        {
            em.close();
        }
	}
    
    @SuppressWarnings("unchecked")
	public List<User> getNewUsers(byte checkUser)
    {
    	List<User> users = null;
    	EntityManager em = JPAResource.factory.createEntityManager();
    	EntityTransaction tx = em.getTransaction();

    	tx.begin();

    	Query q = em.createQuery("select u from User u where u.checkUser = 0");
    	users =  q.getResultList();

    	try 
    	{
    		tx.commit();
    		return users;
    	}
    	catch (PersistenceException e)
    	{
    		if (tx.isActive()) 
    			tx.rollback();
    		return null;
    	}
    	finally 
    	{
    		em.close();
    	}
    }

}

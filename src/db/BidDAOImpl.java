package db;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import entities.Bid;

public class BidDAOImpl {
	
	public int getCountItems()
	{
		EntityManager em = JPAResource.factory.createEntityManager();
	    EntityTransaction tx = em.getTransaction();
	    
	    String query = "Select distinct(bid.item.itemId) from Bid ";
	    Query q = em.createQuery(query);
	    
	    List<Bid> bids = q.getResultList();
	    
        tx.begin();
        try 
        {
            
            tx.commit();
            return bids.size();
        }
        catch (PersistenceException e)
        {
            if (tx.isActive()) tx.rollback();
            return -1;
        }
        finally 
        {
            em.close();
        }
	}
	
	public Long insert(Bid bid)
	{
		EntityManager em = JPAResource.factory.createEntityManager();
	    EntityTransaction tx = em.getTransaction();
	    
        tx.begin();
        try 
        {
            em.persist(bid);
            em.flush();
            tx.commit();
            return bid.getBidId();
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
	
	public int update(Long bidId,String ammount)
	{
		EntityManager em = JPAResource.factory.createEntityManager();
	    EntityTransaction tx = em.getTransaction();
	    
        tx.begin();
        
        Bid bid = em.find(Bid.class, bidId);
        
        bid.setAmount(ammount);
        
        try 
        {
            em.persist(bid);
            em.flush();
            tx.commit();
            return 0;
        }
        catch (PersistenceException e)
        {
            if (tx.isActive()) tx.rollback();
            return -1;
        }
        finally 
        {
            em.close();
        }
	}
	
	public Bid findByBidderAndItem(String userName,Long itemId)
	{
		EntityManager em = JPAResource.factory.createEntityManager();
	    EntityTransaction tx = em.getTransaction();
	    
        tx.begin();
        
        String query = "select b from Bid b where b.user.username LIKE :usern and b.item.itemId = "+itemId;
        Query q = em.createQuery(query);
        q.setParameter("usern", userName);
        
        
        List<Bid> bids = q.getResultList();
        
        try 
        {
            tx.commit();
            if( !bids.isEmpty() )
            	return bids.get(0);
            else
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
	
	public List<Bid> findByItemId(Long itemId)
	{
		EntityManager em = JPAResource.factory.createEntityManager();
	    EntityTransaction tx = em.getTransaction();
	    
        tx.begin();
        
        String query = "select b from Bid b where b.item.itemId = "+itemId;
        Query q = em.createQuery(query);
        
        List<Bid> bids = q.getResultList();
        
        try 
        {
            tx.commit();
            if( !bids.isEmpty() )
            	return bids;
            else
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
	
	public List<Bid> findAll()
	{
		EntityManager em = JPAResource.factory.createEntityManager();
	    EntityTransaction tx = em.getTransaction();
	    
        tx.begin();
        
        Query q = em.createNamedQuery("Bid.findAll");
        
        List<Bid> bids = q.getResultList();
        
        try 
        {
            tx.commit();
            if( !bids.isEmpty() )
            	return bids;
            else
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

}

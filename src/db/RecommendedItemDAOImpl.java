package db;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import entities.Item;
import entities.RecommendedItem;

public class RecommendedItemDAOImpl {
	
	public Long insert(RecommendedItem rec)
	{
		EntityManager em = JPAResource.factory.createEntityManager();
	    EntityTransaction tx = em.getTransaction();
	    
        tx.begin();
        try 
        {
            em.persist(rec);
            em.flush();
            tx.commit();
            return rec.getRecId();
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
	
	public List<Item>  findRecommendations(Long userId)
	{
		EntityManager em = JPAResource.factory.createEntityManager();
	    EntityTransaction tx = em.getTransaction();
	    
	    tx.begin();
	    
	    String query = " select i from Item i, RecommendedItem r where r.itemId = i.itemId and r.userId="+userId.toString();
	    Query q = em.createQuery(query);
	    
	    List<Item> res = q.getResultList();
	    
        try 
        {
            tx.commit();
            return res;
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

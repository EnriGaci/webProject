package db;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

public class ItemsHasCategoryDAOImpl {
	
	public List<String> getItemCategories(Long itemId)
	{
		EntityManager em = JPAResource.factory.createEntityManager();
	    EntityTransaction tx = em.getTransaction();
	    
	    tx.begin();
	    
	    String query = "Select ic.category.name FROM ItemsHasCategory ic Where ic.item.itemId = " + itemId;
        Query q = em.createQuery(query);
        
        List<String> names = q.getResultList();
        
        try 
        {
            tx.commit();
            return names;
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

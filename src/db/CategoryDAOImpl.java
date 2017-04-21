package db;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import entities.Category;
import entities.Item;

public class CategoryDAOImpl {
	
	public Long insertCategory(Category category) 
    {
		EntityManager em = JPAResource.factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try 
        {
            em.persist(category);
            em.flush();
            tx.commit();
            return category.getCategoryId();
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
	
	public Category getCategoryByName(String name)
	{
		Category category = new Category();
        
        EntityManager em = JPAResource.factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        String query = "select c from Category c where c.name LIKE :name";
        Query q = em.createQuery(query);
        q.setParameter("name", name);
        
        List<Category> results = q.getResultList();
        
        try 
        {
            tx.commit();
            
            if(results.size() == 0)
            	return null;
            else
            	return results.get(0);
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

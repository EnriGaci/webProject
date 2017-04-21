package db;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import entities.Item;
import entities.User;

public class ItemDAOImpl 
{
	
	public int deleteById(Long itemId)
	{
		EntityManager em = JPAResource.factory.createEntityManager();
	    EntityTransaction tx = em.getTransaction();
	    
        tx.begin();
        
        Item item = em.find(Item.class, itemId);
        
	    em.remove(item);
	    
	    try 
        {
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
	
	public List<Item> getUserItems(User user)
	{
		EntityManager em = JPAResource.factory.createEntityManager();
	    EntityTransaction tx = em.getTransaction();
	    
        tx.begin();
        
        System.out.println("userid = " + user.getUserId());
        String query = "Select i FROM Item i where i.user.userId = " + user.getUserId();
        Query q = em.createQuery(query);
        
        List<Item> items = q.getResultList();
        
        try 
        {
            tx.commit();
            return items;
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
	
	public Long updateItem(Item item)
	{
		EntityManager em = JPAResource.factory.createEntityManager();
	    EntityTransaction tx = em.getTransaction();
	    
		Item itemDb = null;
        tx.begin();
        
        itemDb = em.find(Item.class, item.getItemId());
        
        itemDb.setBuyPrice(item.getBuyPrice());
        itemDb.setCurrently(item.getCurrently());
        itemDb.setDescription(item.getDescription());
        itemDb.setEnds(item.getEnds());
        itemDb.setFirst_Bid(item.getFirst_Bid());
        itemDb.setLatitude(item.getLatitude());
        itemDb.setLongitude(item.getLongitude());
        itemDb.setName(item.getName());
        itemDb.setNumber_of_Bids(item.getNumber_of_Bids());
        itemDb.setStarted(item.getStarted());
        itemDb.setCountry(item.getCountry());
        itemDb.setBids(item.getBids());
        itemDb.setImages(item.getImages());
        itemDb.setUser(item.getUser());
        itemDb.setItemsHasCategories(item.getItemsHasCategories());
        
        try 
        {
            tx.commit();
            return itemDb.getItemId();
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
	
	public Long updateItemCategories(List<entities.ItemsHasCategory> categories,Long itemId)
	{
		EntityManager em = JPAResource.factory.createEntityManager();
	    EntityTransaction tx = em.getTransaction();
	    
		Item item = null;
        tx.begin();
        
        item = em.find(Item.class, itemId);
        
        item.setItemsHasCategories(categories);
        
        try 
        {
            tx.commit();
            return item.getItemId();
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
	
	public Long insertItem(Item item) 
    {
		EntityManager em = JPAResource.factory.createEntityManager();
	    EntityTransaction tx = em.getTransaction();
	    
        tx.begin();
        try 
        {
            em.persist(item);
            em.flush();
            tx.commit();
            return item.getItemId();
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
	
	public List<Item> getItems()
	{
		List<Item> items = null;
		EntityManager em = JPAResource.factory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();

		//Query q = em.createQuery("Select u from User u");
		Query q = em.createNamedQuery("Item.findAll");
		items =  q.getResultList();
		
		try 
        {
            tx.commit();
            return items;
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
	
	public Item getItemById(Long itemId)
	{
		EntityManager em = JPAResource.factory.createEntityManager();
	    EntityTransaction tx = em.getTransaction();
	    
		Item item = new Item();
        
        tx.begin();
        
        item = em.find(item.getClass() , itemId);
	
        tx.commit();
        em.close();
        
        
        return item;
	}
	
	/*
	 * Kanei update to pedio Started sto item kai to allazei sth vash
	 * 
	 * @return 0 an ola phgan kala alliws gyrnaei 1
	 * 
	 */
	public int updateStarted(Long itemId)
	{
		EntityManager em = JPAResource.factory.createEntityManager();
	    EntityTransaction tx = em.getTransaction();
	    
	    tx.begin();
	    
	    Item item = em.find(Item.class, itemId);
	    
	    item.setStarted(System.currentTimeMillis());
	    
	    try 
        {
            em.persist(item);
            em.flush();
            tx.commit();
            return 0;
        }
        catch (PersistenceException e)
        {
            if (tx.isActive()) 
            	tx.rollback();
            return -1;
        }
        finally 
        {
            em.close();
        }
	}

}

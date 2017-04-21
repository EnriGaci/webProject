package db;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;

import entities.Image;

public class ImageDAOImpl {
	
	public Long insertImage(Image image) 
    {
		EntityManager em = JPAResource.factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try 
        {
            em.persist(image);
            em.flush();
            tx.commit();
            return image.getImageId();
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

}

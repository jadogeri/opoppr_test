/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.svlogic.opoppr.controllers;

import java.io.*;

import jakarta.persistence.*;
import jakarta.persistence.criteria.*;
import java.util.*;
import jakarta.persistence.*;
import jakarta.transaction.*;

import com.svlogic.opoppr.controllers.exceptions.*;
import com.svlogic.opoppr.model.*;

/**
 *
 * @author David
 */
public class NoaPpLat5InventoriesJpaController implements Serializable
{

    public NoaPpLat5InventoriesJpaController(EntityManagerFactory emf)
    {
        this.emf = emf;
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }

    public void create(NoaPpLat5Inventories noaPpLat5Inventories)
    {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            NoaPpLat5 noaPpLat5 = noaPpLat5Inventories.getNoaPpLat5();
            if (noaPpLat5 != null) {
                noaPpLat5 = em.getReference(noaPpLat5.getClass(), noaPpLat5.getNoaPpLat5Id());
                noaPpLat5Inventories.setNoaPpLat5(noaPpLat5);
            }
            em.persist(noaPpLat5Inventories);
            if (noaPpLat5 != null) {
                noaPpLat5.getNoaPpLat5InventoriesCollection().add(noaPpLat5Inventories);
                noaPpLat5 = em.merge(noaPpLat5);
            }
            em.getTransaction().commit();
        }
        finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(NoaPpLat5Inventories noaPpLat5Inventories) throws NonexistentEntityException
    {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            NoaPpLat5Inventories persistentNoaPpLat5Inventories = em.find(NoaPpLat5Inventories.class, noaPpLat5Inventories.getNoaPpLat5InventoriesId());
            NoaPpLat5 noaPpLat5Old = persistentNoaPpLat5Inventories.getNoaPpLat5();
            NoaPpLat5 noaPpLat5New = noaPpLat5Inventories.getNoaPpLat5();
            if (noaPpLat5New != null) {
                noaPpLat5New = em.getReference(noaPpLat5New.getClass(), noaPpLat5New.getNoaPpLat5Id());
                noaPpLat5Inventories.setNoaPpLat5(noaPpLat5New);
            }
            noaPpLat5Inventories = em.merge(noaPpLat5Inventories);
            if (noaPpLat5Old != null && !noaPpLat5Old.equals(noaPpLat5New)) {
                noaPpLat5Old.getNoaPpLat5InventoriesCollection().remove(noaPpLat5Inventories);
                noaPpLat5Old = em.merge(noaPpLat5Old);
            }
            if (noaPpLat5New != null && !noaPpLat5New.equals(noaPpLat5Old)) {
                noaPpLat5New.getNoaPpLat5InventoriesCollection().add(noaPpLat5Inventories);
                noaPpLat5New = em.merge(noaPpLat5New);
            }
            em.getTransaction().commit();
        }
        catch (Throwable t) {
            String msg = t.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = noaPpLat5Inventories.getNoaPpLat5InventoriesId();
                if (findNoaPpLat5Inventories(id) == null) {
                    throw new NonexistentEntityException("The noaPpLat5Inventories with id " + id + " no longer exists.");
                }
            }
            throw new RuntimeException(t);
        }
        finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException
    {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            NoaPpLat5Inventories noaPpLat5Inventories;
            try {
                noaPpLat5Inventories = em.getReference(NoaPpLat5Inventories.class, id);
                noaPpLat5Inventories.getNoaPpLat5InventoriesId();
            }
            catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The noaPpLat5Inventories with id " + id + " no longer exists.", enfe);
            }
            NoaPpLat5 noaPpLat5 = noaPpLat5Inventories.getNoaPpLat5();
            if (noaPpLat5 != null) {
                noaPpLat5.getNoaPpLat5InventoriesCollection().remove(noaPpLat5Inventories);
                noaPpLat5 = em.merge(noaPpLat5);
            }
            em.remove(noaPpLat5Inventories);
            em.getTransaction().commit();
        }
        finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<NoaPpLat5Inventories> findNoaPpLat5InventoriesEntities()
    {
        return findNoaPpLat5InventoriesEntities(true, -1, -1);
    }

    public List<NoaPpLat5Inventories> findNoaPpLat5InventoriesEntities(int maxResults, int firstResult)
    {
        return findNoaPpLat5InventoriesEntities(false, maxResults, firstResult);
    }

    private List<NoaPpLat5Inventories> findNoaPpLat5InventoriesEntities(boolean all, int maxResults, int firstResult)
    {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(NoaPpLat5Inventories.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        }
        finally {
            em.close();
        }
    }

    public NoaPpLat5Inventories findNoaPpLat5Inventories(Integer id)
    {
        EntityManager em = getEntityManager();
        try {
            return em.find(NoaPpLat5Inventories.class, id);
        }
        finally {
            em.close();
        }
    }

    public int getNoaPpLat5InventoriesCount()
    {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<NoaPpLat5Inventories> rt = cq.from(NoaPpLat5Inventories.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        }
        finally {
            em.close();
        }
    }
    
    public int getNoaPpLat5InventoriesCountFilledIn(NoaPpLat5 noaPpLat5)
    {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery cq = cb.createQuery();
            Root<NoaPpLat5Inventories> rt = cq.from(NoaPpLat5Inventories.class);
            cq.select(cb.count(rt));
            
            Predicate p = cb.conjunction();
            p = cb.and(p, cb.equal(rt.get("noaPpLat5"), noaPpLat5));
            p = cb.and(p, cb.isNotNull(rt.get("inventoryAmt")));
            p = cb.and(p, cb.notEqual(rt.get("inventoryAmt"), 0));
            cq.where(p);
            
            Query q = em.createQuery(cq);
            
            return ((Long) q.getSingleResult()).intValue();
        }
        finally {
            em.close();
        }
    }
}

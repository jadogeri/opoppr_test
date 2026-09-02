/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.svlogic.opoppr.controllers;

import java.io.*;
import java.util.*;

import jakarta.persistence.*;
import jakarta.persistence.criteria.*;
import jakarta.transaction.*;

import com.svlogic.opoppr.controllers.exceptions.*;
import com.svlogic.opoppr.model.*;

/**
 *
 * @author David
 */
public class NoaPpLat5FilingJpaController implements Serializable
{

    public NoaPpLat5FilingJpaController(EntityManagerFactory emf)
    {
        this.emf = emf;
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }

    public void create(NoaPpLat5Filing noaPpLat5Filing)
    {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            NoaPpLat5 noaPpLat5 = noaPpLat5Filing.getNoaPpLat5();
            if (noaPpLat5 != null) {
                noaPpLat5 = em.getReference(noaPpLat5.getClass(), noaPpLat5.getNoaPpLat5Id());
                noaPpLat5Filing.setNoaPpLat5(noaPpLat5);
            }
            em.persist(noaPpLat5Filing);
            if (noaPpLat5 != null) {
                noaPpLat5.getNoaPpLat5FilingCollection().add(noaPpLat5Filing);
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

    public void edit(NoaPpLat5Filing noaPpLat5Filing) throws NonexistentEntityException
    {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            NoaPpLat5Filing persistentNoaPpLat5Filing = em.find(NoaPpLat5Filing.class, noaPpLat5Filing.getNoaPpLat5FilingId());
            NoaPpLat5 noaPpLat5Old = persistentNoaPpLat5Filing.getNoaPpLat5();
            NoaPpLat5 noaPpLat5New = noaPpLat5Filing.getNoaPpLat5();
            if (noaPpLat5New != null) {
                noaPpLat5New = em.getReference(noaPpLat5New.getClass(), noaPpLat5New.getNoaPpLat5Id());
                noaPpLat5Filing.setNoaPpLat5(noaPpLat5New);
            }
            noaPpLat5Filing = em.merge(noaPpLat5Filing);
            if (noaPpLat5Old != null && !noaPpLat5Old.equals(noaPpLat5New)) {
                noaPpLat5Old.getNoaPpLat5FilingCollection().remove(noaPpLat5Filing);
                noaPpLat5Old = em.merge(noaPpLat5Old);
            }
            if (noaPpLat5New != null && !noaPpLat5New.equals(noaPpLat5Old)) {
                noaPpLat5New.getNoaPpLat5FilingCollection().add(noaPpLat5Filing);
                noaPpLat5New = em.merge(noaPpLat5New);
            }
            em.getTransaction().commit();
        }
        catch (Throwable t) {
            String msg = t.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = noaPpLat5Filing.getNoaPpLat5FilingId();
                if (findNoaPpLat5Filing(id) == null) {
                    throw new NonexistentEntityException("The noaPpLat5Filing with id " + id + " no longer exists.");
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
            NoaPpLat5Filing noaPpLat5Filing;
            try {
                noaPpLat5Filing = em.getReference(NoaPpLat5Filing.class, id);
                noaPpLat5Filing.getNoaPpLat5FilingId();
            }
            catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The noaPpLat5Filing with id " + id + " no longer exists.", enfe);
            }
            NoaPpLat5 noaPpLat5 = noaPpLat5Filing.getNoaPpLat5();
            if (noaPpLat5 != null) {
                noaPpLat5.getNoaPpLat5FilingCollection().remove(noaPpLat5Filing);
                noaPpLat5 = em.merge(noaPpLat5);
            }
            em.remove(noaPpLat5Filing);
            em.getTransaction().commit();
        }
        finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<NoaPpLat5Filing> findNoaPpLat5FilingEntities()
    {
        return findNoaPpLat5FilingEntities(true, -1, -1);
    }

    public List<NoaPpLat5Filing> findNoaPpLat5FilingEntities(int maxResults, int firstResult)
    {
        return findNoaPpLat5FilingEntities(false, maxResults, firstResult);
    }

    private List<NoaPpLat5Filing> findNoaPpLat5FilingEntities(boolean all, int maxResults, int firstResult)
    {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(NoaPpLat5Filing.class));
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

    public NoaPpLat5Filing findNoaPpLat5Filing(Integer id)
    {
        EntityManager em = getEntityManager();
        try {
            return em.find(NoaPpLat5Filing.class, id);
        }
        finally {
            em.close();
        }
    }

    public int getNoaPpLat5FilingCount()
    {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<NoaPpLat5Filing> rt = cq.from(NoaPpLat5Filing.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        }
        finally {
            em.close();
        }
    }

    public int getNoaPpLat5FilingCountFilledIn(NoaPpLat5 noaPpLat5)
    {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery cq = cb.createQuery();
            Root<NoaPpLat5Filing> rt = cq.from(NoaPpLat5Filing.class);
            cq.select(cb.count(rt));
            
            Predicate p = cb.equal(rt.get("noaPpLat5"), noaPpLat5);
            cq.where(p);
            
            Query q = em.createQuery(cq);
            
            return ((Long) q.getSingleResult()).intValue();
        }
        finally {
            em.close();
        }
    }
}

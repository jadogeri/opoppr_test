/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.svlogic.opoppr.controllers;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import com.svlogic.opoppr.controllers.exceptions.IllegalOrphanException;
import com.svlogic.opoppr.controllers.exceptions.NonexistentEntityException;
import com.svlogic.opoppr.model.UserChange;

/**
 *
 * @author David
 */
public class UserChangeJpaController implements Serializable
{

    public UserChangeJpaController(EntityManagerFactory emf)
    {
        this.emf = emf;
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }

    public void create(UserChange userChange)
    {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(userChange);
            em.getTransaction().commit();
        }
        finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(UserChange userChange) throws NonexistentEntityException, Exception
    {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            UserChange persistentUserChange = em.find(UserChange.class, userChange.getUserChangeId());
            List<String> illegalOrphanMessages = null;
            userChange = em.merge(userChange);
            em.getTransaction().commit();
        }
        catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = userChange.getUserChangeId();
                if (findUserChange(id) == null) {
                    throw new NonexistentEntityException("The user change request with id " + id + " no longer exists.");
                }
            }
            throw ex;
        }
        finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException
    {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            UserChange userChange;
            try {
                userChange = em.getReference(UserChange.class, id);
                userChange.getUserChangeId();
            }
            catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The user change request with id " + id + " no longer exists.", enfe);
            }
            em.remove(userChange);
            em.getTransaction().commit();
        }
        finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<UserChange> findUserChangeEntities()
    {
        return findUserChangeEntities(true, -1, -1);
    }

    public List<UserChange> findUserChangeEntities(int maxResults, int firstResult)
    {
        return findUserChangeEntities(false, maxResults, firstResult);
    }

    @SuppressWarnings("unchecked")
    private List<UserChange> findUserChangeEntities(boolean all, int maxResults, int firstResult)
    {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery<UserChange> cq = em.getCriteriaBuilder().createQuery(UserChange.class);
            cq.select(cq.from(UserChange.class));
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

    public UserChange findUserChange(Integer id)
    {
        EntityManager em = getEntityManager();
        try {
            return em.find(UserChange.class, id);
        }
        finally {
            em.close();
        }
    }

    public int getUserChangeCount()
    {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<UserChange> rt = cq.from(UserChange.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        }
        finally {
            em.close();
        }
    }

    public UserChange findUserChangeByVerificationCode(String verificationCode) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("UserChange.findByVerificationCode");
            q.setParameter("verificationCode", verificationCode);
            return (UserChange)q.getSingleResult();
        }
        catch (NoResultException nre) {
            return null;
        }
        finally {
            em.close();
        }
    }
}

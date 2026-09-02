/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.svlogic.opoppr.controllers;

import com.svlogic.opoppr.controllers.exceptions.*;
import com.svlogic.opoppr.model.*;
import java.io.*;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.*;
import jakarta.persistence.*;
import jakarta.transaction.*;

/**
 *
 * @author David
 */
public class PropertyAssetJpaController implements Serializable
{

    public PropertyAssetJpaController(EntityManagerFactory emf)
    {
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }

    public void create(PropertyAsset propertyAsset)
    {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(propertyAsset);
            em.getTransaction().commit();
        }
        finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PropertyAsset propertyAsset) throws NonexistentEntityException, Exception
    {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            propertyAsset = em.merge(propertyAsset);
            em.getTransaction().commit();
        }
        catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = propertyAsset.getPropertyAssetId();
                if (findPropertyAsset(id) == null) {
                    throw new NonexistentEntityException("The propertyAsset with id " + id + " no longer exists.");
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

    public void destroy(Integer id) throws NonexistentEntityException
    {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PropertyAsset propertyAsset;
            try {
                propertyAsset = em.getReference(PropertyAsset.class, id);
                propertyAsset.getPropertyAssetId();
            }
            catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The propertyAsset with id " + id + " no longer exists.", enfe);
            }
            em.remove(propertyAsset);
            em.getTransaction().commit();
        }
        finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PropertyAsset> findPropertyAssetEntities()
    {
        return findPropertyAssetEntities(true, -1, -1);
    }

    public List<PropertyAsset> findPropertyAssetEntities(int maxResults, int firstResult)
    {
        return findPropertyAssetEntities(false, maxResults, firstResult);
    }

    private List<PropertyAsset> findPropertyAssetEntities(boolean all, int maxResults, int firstResult)
    {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PropertyAsset.class));
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

    public PropertyAsset findPropertyAsset(Integer id)
    {
        EntityManager em = getEntityManager();
        try {
            return em.find(PropertyAsset.class, id);
        }
        finally {
            em.close();
        }
    }

    public int getPropertyAssetCount()
    {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PropertyAsset> rt = cq.from(PropertyAsset.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        }
        finally {
            em.close();
        }
    }
    
    public List<PropertyAsset> findPropertyAssetBySectionNumber(Integer sectionNumber)
    {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("PropertyAsset.findBySectionNumberOrderByPpType");
            q.setParameter("sectionNumber", sectionNumber);
            return q.getResultList();
        }
        finally {
            em.close();
    }
    }
}

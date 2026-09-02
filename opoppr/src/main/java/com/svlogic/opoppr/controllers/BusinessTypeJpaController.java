package com.svlogic.opoppr.controllers;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaQuery;

import com.svlogic.opoppr.model.BusinessType;

/**
 *
 * @author david
 */
public class BusinessTypeJpaController implements Serializable {
    private EntityManagerFactory emf;

    public BusinessTypeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public List<BusinessType> findBusinessTypeEntities() {
        return findBusinessTypeEntities(true, -1, -1);
    }

    public List<BusinessType> findBusinessTypeEntities(int maxResults, int firstResult) {
        return findBusinessTypeEntities(false, maxResults, firstResult);
    }

    private List<BusinessType> findBusinessTypeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(BusinessType.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public BusinessType findBusinessType(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(BusinessType.class, id);
        } finally {
            em.close();
        }
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}

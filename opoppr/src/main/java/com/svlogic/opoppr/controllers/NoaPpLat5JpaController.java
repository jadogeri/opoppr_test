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
import java.util.ArrayList;
import java.util.Collection;
import java.util.*;
import jakarta.persistence.*;
import jakarta.transaction.*;

/**
 *
 * @author David
 */
public class NoaPpLat5JpaController implements Serializable
{

    public NoaPpLat5JpaController(EntityManagerFactory emf)
    {
        this.emf = emf;
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }

    public void create(NoaPpLat5 noaPpLat5)
    {
        if (noaPpLat5.getNoaPpLat5InventoriesCollection() == null) {
            noaPpLat5.setNoaPpLat5InventoriesCollection(new ArrayList<NoaPpLat5Inventories>());
        }
        if (noaPpLat5.getNoaPpLat5FilingCollection() == null) {
            noaPpLat5.setNoaPpLat5FilingCollection(new ArrayList<NoaPpLat5Filing>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Form form = noaPpLat5.getForm();
            if (form != null) {
                form = em.getReference(form.getClass(), form.getFormId());
                noaPpLat5.setForm(form);
            }
            Collection<NoaPpLat5Inventories> attachedNoaPpLat5InventoriesCollection = new ArrayList<NoaPpLat5Inventories>();
            for (NoaPpLat5Inventories noaPpLat5InventoriesCollectionNoaPpLat5InventoriesToAttach : noaPpLat5.getNoaPpLat5InventoriesCollection()) {
                noaPpLat5InventoriesCollectionNoaPpLat5InventoriesToAttach = em.getReference(noaPpLat5InventoriesCollectionNoaPpLat5InventoriesToAttach.getClass(), noaPpLat5InventoriesCollectionNoaPpLat5InventoriesToAttach.getNoaPpLat5InventoriesId());
                attachedNoaPpLat5InventoriesCollection.add(noaPpLat5InventoriesCollectionNoaPpLat5InventoriesToAttach);
            }
            noaPpLat5.setNoaPpLat5InventoriesCollection(attachedNoaPpLat5InventoriesCollection);
            Collection<NoaPpLat5Filing> attachedNoaPpLat5FilingCollection = new ArrayList<NoaPpLat5Filing>();
            for (NoaPpLat5Filing noaPpLat5FilingCollectionNoaPpLat5FilingToAttach : noaPpLat5.getNoaPpLat5FilingCollection()) {
                noaPpLat5FilingCollectionNoaPpLat5FilingToAttach = em.getReference(noaPpLat5FilingCollectionNoaPpLat5FilingToAttach.getClass(), noaPpLat5FilingCollectionNoaPpLat5FilingToAttach.getNoaPpLat5FilingId());
                attachedNoaPpLat5FilingCollection.add(noaPpLat5FilingCollectionNoaPpLat5FilingToAttach);
            }
            noaPpLat5.setNoaPpLat5FilingCollection(attachedNoaPpLat5FilingCollection);
            em.persist(noaPpLat5);
            if (form != null) {
                form.getNoaPpLat5Collection().add(noaPpLat5);
                form = em.merge(form);
            }
            for (NoaPpLat5Inventories noaPpLat5InventoriesCollectionNoaPpLat5Inventories : noaPpLat5.getNoaPpLat5InventoriesCollection()) {
                NoaPpLat5 oldNoaPpLat5OfNoaPpLat5InventoriesCollectionNoaPpLat5Inventories = noaPpLat5InventoriesCollectionNoaPpLat5Inventories.getNoaPpLat5();
                noaPpLat5InventoriesCollectionNoaPpLat5Inventories.setNoaPpLat5(noaPpLat5);
                noaPpLat5InventoriesCollectionNoaPpLat5Inventories = em.merge(noaPpLat5InventoriesCollectionNoaPpLat5Inventories);
                if (oldNoaPpLat5OfNoaPpLat5InventoriesCollectionNoaPpLat5Inventories != null) {
                    oldNoaPpLat5OfNoaPpLat5InventoriesCollectionNoaPpLat5Inventories.getNoaPpLat5InventoriesCollection().remove(noaPpLat5InventoriesCollectionNoaPpLat5Inventories);
                    oldNoaPpLat5OfNoaPpLat5InventoriesCollectionNoaPpLat5Inventories = em.merge(oldNoaPpLat5OfNoaPpLat5InventoriesCollectionNoaPpLat5Inventories);
                }
            }
            for (NoaPpLat5Filing noaPpLat5FilingCollectionNoaPpLat5Filing : noaPpLat5.getNoaPpLat5FilingCollection()) {
                NoaPpLat5 oldNoaPpLat5OfNoaPpLat5FilingCollectionNoaPpLat5Filing = noaPpLat5FilingCollectionNoaPpLat5Filing.getNoaPpLat5();
                noaPpLat5FilingCollectionNoaPpLat5Filing.setNoaPpLat5(noaPpLat5);
                noaPpLat5FilingCollectionNoaPpLat5Filing = em.merge(noaPpLat5FilingCollectionNoaPpLat5Filing);
                if (oldNoaPpLat5OfNoaPpLat5FilingCollectionNoaPpLat5Filing != null) {
                    oldNoaPpLat5OfNoaPpLat5FilingCollectionNoaPpLat5Filing.getNoaPpLat5FilingCollection().remove(noaPpLat5FilingCollectionNoaPpLat5Filing);
                    oldNoaPpLat5OfNoaPpLat5FilingCollectionNoaPpLat5Filing = em.merge(oldNoaPpLat5OfNoaPpLat5FilingCollectionNoaPpLat5Filing);
                }
            }
            em.getTransaction().commit();
        }
        finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(NoaPpLat5 noaPpLat5) throws IllegalOrphanException, NonexistentEntityException
    {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            NoaPpLat5 persistentNoaPpLat5 = em.find(NoaPpLat5.class, noaPpLat5.getNoaPpLat5Id());
            Form formOld = persistentNoaPpLat5.getForm();
            Form formNew = noaPpLat5.getForm();
            Collection<NoaPpLat5Inventories> noaPpLat5InventoriesCollectionOld = persistentNoaPpLat5.getNoaPpLat5InventoriesCollection();
            Collection<NoaPpLat5Inventories> noaPpLat5InventoriesCollectionNew = noaPpLat5.getNoaPpLat5InventoriesCollection();
            Collection<NoaPpLat5Filing> noaPpLat5FilingCollectionOld = persistentNoaPpLat5.getNoaPpLat5FilingCollection();
            Collection<NoaPpLat5Filing> noaPpLat5FilingCollectionNew = noaPpLat5.getNoaPpLat5FilingCollection();
            List<String> illegalOrphanMessages = null;
            for (NoaPpLat5Inventories noaPpLat5InventoriesCollectionOldNoaPpLat5Inventories : noaPpLat5InventoriesCollectionOld) {
                if (!noaPpLat5InventoriesCollectionNew.contains(noaPpLat5InventoriesCollectionOldNoaPpLat5Inventories)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain NoaPpLat5Inventories " + noaPpLat5InventoriesCollectionOldNoaPpLat5Inventories + " since its noaPpLat5 field is not nullable.");
                }
            }
            for (NoaPpLat5Filing noaPpLat5FilingCollectionOldNoaPpLat5Filing : noaPpLat5FilingCollectionOld) {
                if (!noaPpLat5FilingCollectionNew.contains(noaPpLat5FilingCollectionOldNoaPpLat5Filing)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain NoaPpLat5Filing " + noaPpLat5FilingCollectionOldNoaPpLat5Filing + " since its noaPpLat5 field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (formNew != null) {
                formNew = em.getReference(formNew.getClass(), formNew.getFormId());
                noaPpLat5.setForm(formNew);
            }
            Collection<NoaPpLat5Inventories> attachedNoaPpLat5InventoriesCollectionNew = new ArrayList<NoaPpLat5Inventories>();
            for (NoaPpLat5Inventories noaPpLat5InventoriesCollectionNewNoaPpLat5InventoriesToAttach : noaPpLat5InventoriesCollectionNew) {
                noaPpLat5InventoriesCollectionNewNoaPpLat5InventoriesToAttach = em.getReference(noaPpLat5InventoriesCollectionNewNoaPpLat5InventoriesToAttach.getClass(), noaPpLat5InventoriesCollectionNewNoaPpLat5InventoriesToAttach.getNoaPpLat5InventoriesId());
                attachedNoaPpLat5InventoriesCollectionNew.add(noaPpLat5InventoriesCollectionNewNoaPpLat5InventoriesToAttach);
            }
            noaPpLat5InventoriesCollectionNew = attachedNoaPpLat5InventoriesCollectionNew;
            noaPpLat5.setNoaPpLat5InventoriesCollection(noaPpLat5InventoriesCollectionNew);
            Collection<NoaPpLat5Filing> attachedNoaPpLat5FilingCollectionNew = new ArrayList<NoaPpLat5Filing>();
            for (NoaPpLat5Filing noaPpLat5FilingCollectionNewNoaPpLat5FilingToAttach : noaPpLat5FilingCollectionNew) {
                noaPpLat5FilingCollectionNewNoaPpLat5FilingToAttach = em.getReference(noaPpLat5FilingCollectionNewNoaPpLat5FilingToAttach.getClass(), noaPpLat5FilingCollectionNewNoaPpLat5FilingToAttach.getNoaPpLat5FilingId());
                attachedNoaPpLat5FilingCollectionNew.add(noaPpLat5FilingCollectionNewNoaPpLat5FilingToAttach);
            }
            noaPpLat5FilingCollectionNew = attachedNoaPpLat5FilingCollectionNew;
            noaPpLat5.setNoaPpLat5FilingCollection(noaPpLat5FilingCollectionNew);
            noaPpLat5 = em.merge(noaPpLat5);
            if (formOld != null && !formOld.equals(formNew)) {
                formOld.getNoaPpLat5Collection().remove(noaPpLat5);
                formOld = em.merge(formOld);
            }
            if (formNew != null && !formNew.equals(formOld)) {
                formNew.getNoaPpLat5Collection().add(noaPpLat5);
                formNew = em.merge(formNew);
            }
            for (NoaPpLat5Inventories noaPpLat5InventoriesCollectionNewNoaPpLat5Inventories : noaPpLat5InventoriesCollectionNew) {
                if (!noaPpLat5InventoriesCollectionOld.contains(noaPpLat5InventoriesCollectionNewNoaPpLat5Inventories)) {
                    NoaPpLat5 oldNoaPpLat5OfNoaPpLat5InventoriesCollectionNewNoaPpLat5Inventories = noaPpLat5InventoriesCollectionNewNoaPpLat5Inventories.getNoaPpLat5();
                    noaPpLat5InventoriesCollectionNewNoaPpLat5Inventories.setNoaPpLat5(noaPpLat5);
                    noaPpLat5InventoriesCollectionNewNoaPpLat5Inventories = em.merge(noaPpLat5InventoriesCollectionNewNoaPpLat5Inventories);
                    if (oldNoaPpLat5OfNoaPpLat5InventoriesCollectionNewNoaPpLat5Inventories != null && !oldNoaPpLat5OfNoaPpLat5InventoriesCollectionNewNoaPpLat5Inventories.equals(noaPpLat5)) {
                        oldNoaPpLat5OfNoaPpLat5InventoriesCollectionNewNoaPpLat5Inventories.getNoaPpLat5InventoriesCollection().remove(noaPpLat5InventoriesCollectionNewNoaPpLat5Inventories);
                        oldNoaPpLat5OfNoaPpLat5InventoriesCollectionNewNoaPpLat5Inventories = em.merge(oldNoaPpLat5OfNoaPpLat5InventoriesCollectionNewNoaPpLat5Inventories);
                    }
                }
            }
            for (NoaPpLat5Filing noaPpLat5FilingCollectionNewNoaPpLat5Filing : noaPpLat5FilingCollectionNew) {
                if (!noaPpLat5FilingCollectionOld.contains(noaPpLat5FilingCollectionNewNoaPpLat5Filing)) {
                    NoaPpLat5 oldNoaPpLat5OfNoaPpLat5FilingCollectionNewNoaPpLat5Filing = noaPpLat5FilingCollectionNewNoaPpLat5Filing.getNoaPpLat5();
                    noaPpLat5FilingCollectionNewNoaPpLat5Filing.setNoaPpLat5(noaPpLat5);
                    noaPpLat5FilingCollectionNewNoaPpLat5Filing = em.merge(noaPpLat5FilingCollectionNewNoaPpLat5Filing);
                    if (oldNoaPpLat5OfNoaPpLat5FilingCollectionNewNoaPpLat5Filing != null && !oldNoaPpLat5OfNoaPpLat5FilingCollectionNewNoaPpLat5Filing.equals(noaPpLat5)) {
                        oldNoaPpLat5OfNoaPpLat5FilingCollectionNewNoaPpLat5Filing.getNoaPpLat5FilingCollection().remove(noaPpLat5FilingCollectionNewNoaPpLat5Filing);
                        oldNoaPpLat5OfNoaPpLat5FilingCollectionNewNoaPpLat5Filing = em.merge(oldNoaPpLat5OfNoaPpLat5FilingCollectionNewNoaPpLat5Filing);
                    }
                }
            }
            em.getTransaction().commit();
        }
        catch (Throwable t) {
            String msg = t.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = noaPpLat5.getNoaPpLat5Id();
                if (findNoaPpLat5(id) == null) {
                    throw new NonexistentEntityException("The noaPpLat5 with id " + id + " no longer exists.");
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

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException
    {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            NoaPpLat5 noaPpLat5;
            try {
                noaPpLat5 = em.getReference(NoaPpLat5.class, id);
                noaPpLat5.getNoaPpLat5Id();
            }
            catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The noaPpLat5 with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<NoaPpLat5Inventories> noaPpLat5InventoriesCollectionOrphanCheck = noaPpLat5.getNoaPpLat5InventoriesCollection();
            for (NoaPpLat5Inventories noaPpLat5InventoriesCollectionOrphanCheckNoaPpLat5Inventories : noaPpLat5InventoriesCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This NoaPpLat5 (" + noaPpLat5 + ") cannot be destroyed since the NoaPpLat5Inventories " + noaPpLat5InventoriesCollectionOrphanCheckNoaPpLat5Inventories + " in its noaPpLat5InventoriesCollection field has a non-nullable noaPpLat5 field.");
            }
            Collection<NoaPpLat5Filing> noaPpLat5FilingCollectionOrphanCheck = noaPpLat5.getNoaPpLat5FilingCollection();
            for (NoaPpLat5Filing noaPpLat5FilingCollectionOrphanCheckNoaPpLat5Filing : noaPpLat5FilingCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This NoaPpLat5 (" + noaPpLat5 + ") cannot be destroyed since the NoaPpLat5Filing " + noaPpLat5FilingCollectionOrphanCheckNoaPpLat5Filing + " in its noaPpLat5FilingCollection field has a non-nullable noaPpLat5 field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Form form = noaPpLat5.getForm();
            if (form != null) {
                form.getNoaPpLat5Collection().remove(noaPpLat5);
                form = em.merge(form);
            }
            em.remove(noaPpLat5);
            em.getTransaction().commit();
        }
        finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<NoaPpLat5> findNoaPpLat5Entities()
    {
        return findNoaPpLat5Entities(true, -1, -1);
    }

    public List<NoaPpLat5> findNoaPpLat5Entities(int maxResults, int firstResult)
    {
        return findNoaPpLat5Entities(false, maxResults, firstResult);
    }

    private List<NoaPpLat5> findNoaPpLat5Entities(boolean all, int maxResults, int firstResult)
    {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(NoaPpLat5.class));
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

    public NoaPpLat5 findNoaPpLat5(Integer id)
    {
        EntityManager em = getEntityManager();
        try {
            return em.find(NoaPpLat5.class, id);
        }
        finally {
            em.close();
        }
    }

    public int getNoaPpLat5Count()
    {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<NoaPpLat5> rt = cq.from(NoaPpLat5.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        }
        finally {
            em.close();
        }
    }
    
}

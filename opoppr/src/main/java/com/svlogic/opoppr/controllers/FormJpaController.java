package com.svlogic.opoppr.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
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
import com.svlogic.opoppr.model.Form;
import com.svlogic.opoppr.model.FormType;
import com.svlogic.opoppr.model.NoaPpLat5;
import com.svlogic.opoppr.model.Status;
import com.svlogic.opoppr.model.User;

/**
 *
 * @author David
 */
public class FormJpaController implements Serializable
{

    public FormJpaController(EntityManagerFactory emf)
    {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }

    public void create(Form form)
    {
        if (form.getNoaPpLat5Collection() == null) {
            form.setNoaPpLat5Collection(new ArrayList<NoaPpLat5>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FormType formType = form.getFormType();
            if (formType != null) {
                formType = em.getReference(formType.getClass(), formType.getFormTypeId());
                form.setFormType(formType);
            }
            Status status = form.getStatus();
            if (status != null) {
                status = em.getReference(status.getClass(), status.getStatusId());
                form.setStatus(status);
            }
            User user = form.getUserId();
            if (user != null) {
                user = em.getReference(user.getClass(), user.getUserId());
                form.setUserId(user);
            }
            List<NoaPpLat5> attachedNoaPpLat5Collection = new ArrayList<NoaPpLat5>();
            for (NoaPpLat5 noaPpLat5CollectionNoaPpLat5ToAttach : form.getNoaPpLat5Collection()) {
                noaPpLat5CollectionNoaPpLat5ToAttach = em.getReference(noaPpLat5CollectionNoaPpLat5ToAttach.getClass(), noaPpLat5CollectionNoaPpLat5ToAttach.getNoaPpLat5Id());
                attachedNoaPpLat5Collection.add(noaPpLat5CollectionNoaPpLat5ToAttach);
            }
            form.setNoaPpLat5Collection(attachedNoaPpLat5Collection);
            em.persist(form);
            if (user != null) {
                user.getFormCollection().add(form);
                user = em.merge(user);
            }
            for (NoaPpLat5 noaPpLat5CollectionNoaPpLat5 : form.getNoaPpLat5Collection()) {
                Form oldFormOfNoaPpLat5CollectionNoaPpLat5 = noaPpLat5CollectionNoaPpLat5.getForm();
                noaPpLat5CollectionNoaPpLat5.setForm(form);
                noaPpLat5CollectionNoaPpLat5 = em.merge(noaPpLat5CollectionNoaPpLat5);
                if (oldFormOfNoaPpLat5CollectionNoaPpLat5 != null) {
                    oldFormOfNoaPpLat5CollectionNoaPpLat5.getNoaPpLat5Collection().remove(noaPpLat5CollectionNoaPpLat5);
                    oldFormOfNoaPpLat5CollectionNoaPpLat5 = em.merge(oldFormOfNoaPpLat5CollectionNoaPpLat5);
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

    public void edit(Form form) throws IllegalOrphanException, NonexistentEntityException
    {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Form persistentForm = em.find(Form.class, form.getFormId());
            FormType formTypeOld = persistentForm.getFormType();
            FormType formTypeNew = form.getFormType();
            Status statusNew = form.getStatus();
            User userOld = persistentForm.getUserId();
            User userNew = form.getUserId();
            List<NoaPpLat5> noaPpLat5CollectionOld = persistentForm.getNoaPpLat5Collection();
            List<NoaPpLat5> noaPpLat5CollectionNew = form.getNoaPpLat5Collection();
            List<String> illegalOrphanMessages = null;
            for (NoaPpLat5 noaPpLat5CollectionOldNoaPpLat5 : noaPpLat5CollectionOld) {
                if (!noaPpLat5CollectionNew.contains(noaPpLat5CollectionOldNoaPpLat5)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain NoaPpLat5 " + noaPpLat5CollectionOldNoaPpLat5 + " since its form field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (formTypeNew != null) {
                formTypeNew = em.getReference(formTypeNew.getClass(), formTypeNew.getFormTypeId());
                form.setFormType(formTypeNew);
            }
            if (statusNew != null) {
                statusNew = em.getReference(statusNew.getClass(), statusNew.getStatusId());
                form.setStatus(statusNew);
            }
            if (userNew != null) {
                userNew = em.getReference(userNew.getClass(), userNew.getUserId());
                form.setUserId(userNew);
            }
            List<NoaPpLat5> attachedNoaPpLat5CollectionNew = new ArrayList<NoaPpLat5>();
            for (NoaPpLat5 noaPpLat5CollectionNewNoaPpLat5ToAttach : noaPpLat5CollectionNew) {
                noaPpLat5CollectionNewNoaPpLat5ToAttach = em.getReference(noaPpLat5CollectionNewNoaPpLat5ToAttach.getClass(), noaPpLat5CollectionNewNoaPpLat5ToAttach.getNoaPpLat5Id());
                attachedNoaPpLat5CollectionNew.add(noaPpLat5CollectionNewNoaPpLat5ToAttach);
            }
            noaPpLat5CollectionNew = attachedNoaPpLat5CollectionNew;
            form.setNoaPpLat5Collection(noaPpLat5CollectionNew);
            form = em.merge(form);
            if (userOld != null && !userOld.equals(userNew)) {
                userOld.getFormCollection().remove(form);
                userOld = em.merge(userOld);
            }
            if (userNew != null && !userNew.equals(userOld)) {
                userNew.getFormCollection().add(form);
                userNew = em.merge(userNew);
            }
            for (NoaPpLat5 noaPpLat5CollectionNewNoaPpLat5 : noaPpLat5CollectionNew) {
                if (!noaPpLat5CollectionOld.contains(noaPpLat5CollectionNewNoaPpLat5)) {
                    Form oldFormOfNoaPpLat5CollectionNewNoaPpLat5 = noaPpLat5CollectionNewNoaPpLat5.getForm();
                    noaPpLat5CollectionNewNoaPpLat5.setForm(form);
                    noaPpLat5CollectionNewNoaPpLat5 = em.merge(noaPpLat5CollectionNewNoaPpLat5);
                    if (oldFormOfNoaPpLat5CollectionNewNoaPpLat5 != null && !oldFormOfNoaPpLat5CollectionNewNoaPpLat5.equals(form)) {
                        oldFormOfNoaPpLat5CollectionNewNoaPpLat5.getNoaPpLat5Collection().remove(noaPpLat5CollectionNewNoaPpLat5);
                        oldFormOfNoaPpLat5CollectionNewNoaPpLat5 = em.merge(oldFormOfNoaPpLat5CollectionNewNoaPpLat5);
                    }
                }
            }
            em.getTransaction().commit();
        }
        catch (Throwable t) {
            String msg = t.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = form.getFormId();
                if (findForm(id) == null) {
                    throw new NonexistentEntityException("The form with id " + id + " no longer exists.");
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
            Form form;
            try {
                form = em.getReference(Form.class, id);
                form.getFormId();
            }
            catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The form with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<NoaPpLat5> noaPpLat5CollectionOrphanCheck = form.getNoaPpLat5Collection();
            for (NoaPpLat5 noaPpLat5CollectionOrphanCheckNoaPpLat5 : noaPpLat5CollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Form (" + form + ") cannot be destroyed since the NoaPpLat5 " + noaPpLat5CollectionOrphanCheckNoaPpLat5 + " in its noaPpLat5Collection field has a non-nullable form field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            User user = form.getUserId();
            if (user != null) {
                user.getFormCollection().remove(form);
                user = em.merge(user);
            }
            em.remove(form);
            em.getTransaction().commit();
        }
        finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Form> findFormEntities()
    {
        return findFormEntities(true, -1, -1);
    }

    public List<Form> findFormEntities(int maxResults, int firstResult)
    {
        return findFormEntities(false, maxResults, firstResult);
    }

    private List<Form> findFormEntities(boolean all, int maxResults, int firstResult)
    {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Form.class));
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

    public List<Form> findFormsByFilingYear(int filingYear) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("Form.findByFilingYear");
            q.setParameter("filingYear", filingYear);
            return q.getResultList();
        }
        finally {
            em.close();
        }
    }

    public Form findForm(Integer id)
    {
        EntityManager em = getEntityManager();
        try {
            return em.find(Form.class, id);
        }
        finally {
            em.close();
        }
    }

    public Form findFormByBillNumber(String billNumber)
    {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("Form.findByBillNumber");
            q.setParameter("billNumber", billNumber);
            return (Form)q.getSingleResult();
        }
        catch (NoResultException nre) {
            return null;
        }
        finally {
            em.close();
        }
    }

    public Form findFormByBillNumberAndPIN(String billNumber, String pin)
    {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("Form.findByBillNumberAndPin");
            q.setParameter("billNumber", billNumber);
            q.setParameter("pin", pin);
            return (Form)q.getSingleResult();
        }
        catch (NoResultException nre) {
            return null;
        }
        finally {
            em.close();
        }
    }


    public Form findFormByBillNumberAndPINAndStatusName(String billNumber, String pin, String statusName)
    {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("Form.findByBillNumberAndPinAndStatus");
            q.setParameter("billNumber", billNumber);
            q.setParameter("pin", pin);
            q.setParameter("statusName", statusName);
            return (Form)q.getSingleResult();
        }
        catch (NoResultException nre) {
            return null;
        }
        finally {
            em.close();
        }
    }

    public Form findFormByBillNumberAndFilingYear(String billNumber, int filingYear) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("Form.findByBillNumberAndFilingYear");
            q.setParameter("billNumber", billNumber);
            q.setParameter("filingYear", filingYear);
            return (Form)q.getSingleResult();
        }
        catch (NoResultException nre) {
            return null;
        }
        finally {
            em.close();
        }
    }

    public int getFormCount()
    {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Form> rt = cq.from(Form.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        }
        finally {
            em.close();
        }
    }
    
}

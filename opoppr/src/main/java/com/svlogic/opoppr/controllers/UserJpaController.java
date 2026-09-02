/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
import com.svlogic.opoppr.model.User;

/**
 *
 * @author David
 */
public class UserJpaController implements Serializable
{

    public UserJpaController(EntityManagerFactory emf)
    {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }

    public void create(User user)
    {
        if (user.getFormCollection() == null) {
            user.setFormCollection(new ArrayList<Form>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Form> attachedFormCollection = new ArrayList<Form>();
            for (Form formCollectionFormToAttach : user.getFormCollection()) {
                formCollectionFormToAttach = em.getReference(formCollectionFormToAttach.getClass(), formCollectionFormToAttach.getFormId());
                attachedFormCollection.add(formCollectionFormToAttach);
            }
            user.setFormCollection(attachedFormCollection);
            em.persist(user);
            for (Form formCollectionForm : user.getFormCollection()) {
                User oldUserOfFormCollectionForm = formCollectionForm.getUserId();
                formCollectionForm.setUserId(user);
                formCollectionForm = em.merge(formCollectionForm);
                if (oldUserOfFormCollectionForm != null) {
                    oldUserOfFormCollectionForm.getFormCollection().remove(formCollectionForm);
                    oldUserOfFormCollectionForm = em.merge(oldUserOfFormCollectionForm);
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

    public void edit(User user) throws IllegalOrphanException, NonexistentEntityException
    {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User persistentUser = em.find(User.class, user.getUserId());
            Collection<Form> formCollectionOld = persistentUser.getFormCollection();
            Collection<Form> formCollectionNew = user.getFormCollection();
            List<String> illegalOrphanMessages = null;
            for (Form formCollectionOldForm : formCollectionOld) {
                if (!formCollectionNew.contains(formCollectionOldForm)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Form " + formCollectionOldForm + " since its user field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Form> attachedFormCollectionNew = new ArrayList<Form>();
            for (Form formCollectionNewFormToAttach : formCollectionNew) {
                formCollectionNewFormToAttach = em.getReference(formCollectionNewFormToAttach.getClass(), formCollectionNewFormToAttach.getFormId());
                attachedFormCollectionNew.add(formCollectionNewFormToAttach);
            }
            formCollectionNew = attachedFormCollectionNew;
            user.setFormCollection(formCollectionNew);
            user = em.merge(user);
            for (Form formCollectionNewForm : formCollectionNew) {
                if (!formCollectionOld.contains(formCollectionNewForm)) {
                    User oldUserOfAccountCollectionNewAccount = formCollectionNewForm.getUserId();
                    formCollectionNewForm.setUserId(user);
                    formCollectionNewForm = em.merge(formCollectionNewForm);
                    if (oldUserOfAccountCollectionNewAccount != null && !oldUserOfAccountCollectionNewAccount.equals(user)) {
                        oldUserOfAccountCollectionNewAccount.getFormCollection().remove(formCollectionNewForm);
                        oldUserOfAccountCollectionNewAccount = em.merge(oldUserOfAccountCollectionNewAccount);
                    }
                }
            }
            em.getTransaction().commit();
        }
        catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = user.getUserId();
                if (findUser(id) == null) {
                    throw new NonexistentEntityException("The user with id " + id + " no longer exists.");
                }
            }
            throw new RuntimeException(ex);
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
            User user;
            try {
                user = em.getReference(User.class, id);
                user.getUserId();
            }
            catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The account with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Form> formCollectionOrphanCheck = user.getFormCollection();
            for (Form formCollectionOrphanCheckForm : formCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This User (" + user + ") cannot be destroyed since the Form " + formCollectionOrphanCheckForm + " in its formCollection field has a non-nullable user field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(user);
            em.getTransaction().commit();
        }
        finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<User> findUserEntities()
    {
        return findUserEntities(true, -1, -1);
    }

    public List<User> findUserEntities(int maxResults, int firstResult)
    {
        return findUserEntities(false, maxResults, firstResult);
    }

    @SuppressWarnings("unchecked")
    private List<User> findUserEntities(boolean all, int maxResults, int firstResult)
    {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery<User> cq = em.getCriteriaBuilder().createQuery(User.class);
            cq.select(cq.from(User.class));
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

    public User findUser(Integer id)
    {
        EntityManager em = getEntityManager();
        try {
            return em.find(User.class, id);
        }
        finally {
            em.close();
        }
    }

    public int getUserCount()
    {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<User> rt = cq.from(User.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        }
        finally {
            em.close();
        }
    }

    public User findUserByUsername(String username)
    {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("User.findByUsername");
            q.setParameter("username", username);
            return (User)q.getSingleResult();
        }
        catch (NoResultException nre) {
            return null;
        }
        finally {
            em.close();
        }
    }
    
    public User findUserByUsernameAndPassword(String username, String password)
    {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("User.findByUsernameAndPassword");
            q.setParameter("username", username);
            q.setParameter("password", password);
            return (User)q.getSingleResult();
        }
        catch (NoResultException nre) {
            return null;
        }
        finally {
            em.close();
        }
    }
    
    public User findUserByEmailAddress(String emailAddress) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("User.findByEmailAddress");
            q.setParameter("emailAddress", emailAddress);
            return (User)q.getSingleResult();
        }
        catch (NoResultException nre) {
            return null;
        }
        finally {
            em.close();
        }
    }

    public User findUserByUsernameAndEnabled(String username) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("User.findByUsernameAndEnabled");
            q.setParameter("username", username);
            return (User)q.getSingleResult();
        }
        catch (NoResultException nre) {
            return null;
        }
        finally {
            em.close();
        }
    }
}

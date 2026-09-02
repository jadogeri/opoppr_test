package com.svlogic.opoppr.admin;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import com.svlogic.opoppr.annotation.CurrentUserSession;
import com.svlogic.opoppr.app.AppListener;
import com.svlogic.opoppr.model.Form;
import com.svlogic.opoppr.model.User;
import com.svlogic.opoppr.session.UserSession;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.model.SelectItem;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Named("lat5List")
@SessionScoped
public class LAT5List implements Serializable {
    private Collection<Form> filteredForms;
    private UserSession userSession;
    private EntityManager entityManager;
    private int filingYear;
    private User selectedUser;

    public LAT5List() {
        this.entityManager = AppListener.getEntityManagerFactory().createEntityManager();
    }

    public Collection<Form> getForms() {
        return getUserSession().getAllForms(getFilingYear());
    }

    public Collection<Form> getFilteredForms() {
        return filteredForms;
    }

    public void setFilteredForms(Collection<Form> filteredForms) {
        this.filteredForms = filteredForms;
    }

    public Collection<SelectItem> getStatuses() {

        return Arrays.asList(new SelectItem("", "All"),
                new SelectItem("New"),
                new SelectItem("In Progress"),
                new SelectItem("Submitted"),
                new SelectItem("Closed"));
    }

    public String selectForm(Form form) {
        userSession.setCurrentForm(form);
        return form.getFormType().getFormName();
    }

    public int getFilingYear() {
        return filingYear;
    }

    public void setFilingYear(int filingYear) {
        this.filingYear = filingYear;
    }

    public String search() {
        return null;
    }

    @SuppressWarnings("unchecked")
    public Collection<String> getFilingYears() {
        EntityManager em = getEntityManager();
        CriteriaQuery<String> cq = em.getCriteriaBuilder().createQuery(String.class);
        Root<Form> rt = cq.from(Form.class);
        cq.select(rt.get("filingYear")).distinct(true);
        cq.orderBy(em.getCriteriaBuilder().desc(rt.get("filingYear")));
        Query q = em.createQuery(cq);
        return q.getResultList();
    }

    public UserSession getUserSession() {
        return userSession;
    }

    @Inject
    public void setUserSession(@CurrentUserSession UserSession userSession) {
        this.userSession = userSession;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public String viewLat5Form(Form f) {
        userSession.setCurrentForm(f);   
        return "lat5Detail?faces-redirect=true";
    }

    public void prepareFilerNavigation(User user) {
        this.selectedUser = user;       
    }

    public User getSelectedUser() {
        return selectedUser;
    }
}

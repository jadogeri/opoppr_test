package com.svlogic.opoppr.admin;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.primefaces.component.tabview.TabView;
import com.svlogic.opoppr.app.AppListener;
import com.svlogic.opoppr.controllers.FormJpaController;
import com.svlogic.opoppr.controllers.UserJpaController;
import com.svlogic.opoppr.model.Form;
import com.svlogic.opoppr.model.User;
import com.svlogic.opoppr.model.UserRole;
import com.svlogic.opoppr.session.UserSession;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Named("filerList")
@SessionScoped
public class FilerList implements Serializable {
    private Collection<Form> filteredForms;
    private UserSession userSession;
    private EntityManager entityManager;
    private Collection<User> filteredUsers; 
    private int activeTab = 0; 
    private TabView tabView; 


    public FilerList() {
        this.entityManager = AppListener.getEntityManagerFactory().createEntityManager();
    }

    public Collection<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();

        UserJpaController userJpaController = new UserJpaController(AppListener.getEntityManagerFactory());
        users.addAll(userJpaController.findUserEntities()
            .stream()
            .filter(u -> 
                u.getUserRoleId().getUserRoleId() != null && 
                u.getUserRoleId().getUserRoleId() == UserRole.TAX_PREPARER.getUserRoleId()).toList());

        users.sort((f1, f2) -> {
            int result = -f1.getUsername().compareTo(f2.getUsername());
            if (result == 0) {
                result = f1.getEmailAddress().compareTo(f2.getEmailAddress());
            }
            return result;
        });

        return users;
    }

    public int totalForms(User user) {
        // If the user or their form collection is null, return 0 instead of crashing
        if (user == null || user.getFormCollection() == null) {
            return 0;
        }
        return user.getFormCollection().size();
    }

    public Collection<Form> getForms() {
        ArrayList<Form> ret = new ArrayList<Form>();
        FormJpaController formJpaController = new FormJpaController(AppListener.getEntityManagerFactory());

        for(String year : getFilingYears()) {
            int filingYear = Integer.parseInt(year);
            ret.addAll(formJpaController.findFormsByFilingYear(filingYear)
                .stream()
                .filter(f -> f.getUserId() != null && f.getUserId().getUserId() != null)
                .collect(Collectors.toList()));
        }


        ret.sort((f1, f2) -> {
            int result = -Integer.compare(f1.getFilingYear(), f2.getFilingYear());
            if (result == 0) {
                result = Integer.compare(f1.getStatus().getStatusId(), f2.getStatus().getStatusId());
            }
            return result;
        });

        return ret;
    }
    

    public Collection<Form> getFilteredForms() {  return filteredForms; }

    public void setFilteredForms(Collection<Form> filteredForms) {  this.filteredForms = filteredForms; }

    public String selectForm(Form form) {
        userSession.setCurrentForm(form);
        return form.getFormType().getFormName();
    } 

    @SuppressWarnings("unchecked")
    public Collection<String> getFilingYears() {
        EntityManager em = getEntityManager();

        CriteriaQuery<Integer> cq = em.getCriteriaBuilder().createQuery(Integer.class);
        Root<Form> rt = cq.from(Form.class);
        
        cq.select(rt.get("filingYear")).distinct(true);
        cq.orderBy(em.getCriteriaBuilder().desc(rt.get("filingYear")));
        
        List<Integer> results = em.createQuery(cq).getResultList();

        return results.stream()
            .map(String::valueOf)
            .collect(Collectors.toCollection(ArrayList::new));
    }


    public Collection<User> getFilteredUsers() {  return filteredUsers; }
    
    public void setFilteredUsers(Collection<User> filteredUsers) {  this.filteredUsers = filteredUsers; }

    public UserSession getUserSession() {  return userSession; }           

    public TabView getTabView() {  return tabView; }

    public void setTabView(TabView tabView) {  this.tabView = tabView; }

    public Integer getActiveTab() { return activeTab; }

    public void setActiveTab(Integer activeTab) { this.activeTab = activeTab; }


    public EntityManager getEntityManager() {  return entityManager; }    
    public void setEntityManager(EntityManager entityManager) {  this.entityManager = entityManager; }

}

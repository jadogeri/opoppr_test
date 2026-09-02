package com.svlogic.opoppr.admin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import com.svlogic.opoppr.app.AppListener;
import com.svlogic.opoppr.controllers.FormJpaController;
import com.svlogic.opoppr.controllers.UserJpaController;
import com.svlogic.opoppr.model.Form;
import com.svlogic.opoppr.model.User;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

@Named("filerDetail")
@RequestScoped
public class FilerDetail implements Serializable {

    private String searchEmailAddress; 
    private User selectedUser;
    private Map<String,Collection<Form>> userForms;

    public String getSearchEmailAddress() { return searchEmailAddress; }
    public void setSearchEmailAddress(String email) { this.searchEmailAddress = email; }

    public User getSelectedUser() { return selectedUser; }
    public void setSelectedUser(User user) { this.selectedUser = user; }

    public Map<String,Collection<Form>> getUserForms() { return userForms; }
    public void setUserForms(Map<String,Collection<Form>> userForms) { this.userForms = userForms; }

     public void performSearch() {
        if (searchEmailAddress == null || searchEmailAddress.isEmpty()) {
            return;
        }
        
        UserJpaController userJpaController = new UserJpaController(AppListener.getEntityManagerFactory());         
        this.selectedUser = userJpaController.findUserByEmailAddress(searchEmailAddress);
        if (this.selectedUser == null) {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_WARN, "Not Found", "No user profile found for the provided email address."));
        }else{
            FormJpaController formJpaController = new FormJpaController(AppListener.getEntityManagerFactory());
            this.userForms = formJpaController.findFormEntities()
                .stream()
                .filter(f -> f.getUserId() != null && f.getUserId().equals(this.selectedUser))
                .collect(Collectors.groupingBy(
                    f -> String.valueOf(f.getFilingYear()),
                    Collectors.toCollection(ArrayList::new) 
                ));
        }

    }

    public void clearSearch() {
        this.searchEmailAddress = null;
        this.selectedUser = null;
        this.userForms = null;
    }


}

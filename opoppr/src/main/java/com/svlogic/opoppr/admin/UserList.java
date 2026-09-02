package com.svlogic.opoppr.admin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import com.svlogic.opoppr.annotation.CurrentUserSession;
import com.svlogic.opoppr.app.AppListener;
import com.svlogic.opoppr.controllers.UserJpaController;
import com.svlogic.opoppr.model.User;
import com.svlogic.opoppr.session.UserSession;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.model.SelectItem;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;

@Named("userList")
@SessionScoped
public class UserList implements Serializable {

    private Collection<User> filteredUsers; 
    private UserSession userSession;
    private EntityManager entityManager;
    private User selectedUser;
    private boolean isEditable;

 
    public UserList() {
        this.entityManager = AppListener.getEntityManagerFactory().createEntityManager();
    }


    public Collection<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();

        UserJpaController userJpaController = new UserJpaController(AppListener.getEntityManagerFactory());
        users.addAll(userJpaController.findUserEntities());

        users.sort((f1, f2) -> {
            int result = -f1.getUsername().compareTo(f2.getUsername());
            if (result == 0) {
                result = f1.getEmailAddress().compareTo(f2.getEmailAddress());
            }
            return result;
        });

        return users;
    }

    public Collection<User> getFilteredUsers() {
        return filteredUsers;
    }
    
    public void setFilteredUsers(Collection<User> filteredUsers) {
        this.filteredUsers = filteredUsers;
    }

    public String selectUser(User user, Boolean editState) {
        this.setIsEditable(editState);
        this.selectedUser = user;
        return "adminUserProfile";
    }

    public boolean getIsEditable(){
        return this.isEditable;
    }
    public void setIsEditable(Boolean isEditable){
        this.isEditable = isEditable;
    }

    public User getSelectedUser() { return selectedUser; }
    public void setSelectedUser(User selectedUser) { this.selectedUser = selectedUser; }


    public Collection<SelectItem> getStatuses() {
        return Arrays.asList(
                new SelectItem("Disabled"),
                new SelectItem("Enabled"),
                new SelectItem("Locked"));
    }

    public Collection<SelectItem> getRoles() {
        return Arrays.asList(
                new SelectItem("Administrator"),
                new SelectItem("Superuser"),
                new SelectItem("Tax Preparer"));

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

}

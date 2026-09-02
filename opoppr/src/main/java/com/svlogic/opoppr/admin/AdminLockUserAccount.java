package com.svlogic.opoppr.admin;


import com.svlogic.opoppr.annotation.CurrentUserSession;
import com.svlogic.opoppr.model.User;
import com.svlogic.opoppr.session.UserSession;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named("adminLockUserAccount")
@RequestScoped
public class AdminLockUserAccount{

    private UserSession userSession;
    private boolean lockUserAccountFailed;


    public AdminLockUserAccount() {
    }   

    public String lock(User user) {
        String ret = "success";
        boolean accountLocked = this.getUserSession().adminLockUserAccount(user);
        if (!accountLocked) {
            ret = "failure";
            this.setLockUserAccountFailed(true);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Unable to lock account for " + user.getEmailAddress() + ".", "Administration Action"));
            return ret;
        }

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Account for " + user.getEmailAddress() + " has been disabled.", "Administration Action"));
        
        return ret;
    }

    public boolean isLockUserAccountFailed() {
        return lockUserAccountFailed; 
    }

    public void setLockUserAccountFailed(boolean lockUserAccountFailed) {
        this.lockUserAccountFailed = lockUserAccountFailed; 
    }

    public UserSession getUserSession()
    {
        return userSession;
    }

    @Inject
    public void setUserSession(@CurrentUserSession UserSession userSession)
    {
        this.userSession = userSession;
    }

}


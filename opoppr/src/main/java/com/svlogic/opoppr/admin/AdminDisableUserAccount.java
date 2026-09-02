package com.svlogic.opoppr.admin;


import com.svlogic.opoppr.annotation.CurrentUserSession;
import com.svlogic.opoppr.model.User;
import com.svlogic.opoppr.session.UserSession;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named("adminDisableUserAccount")
@RequestScoped
public class AdminDisableUserAccount{

    private UserSession userSession;
    private boolean disableUserAccountFailed;


    public AdminDisableUserAccount() {
    }   

    public String disable(User user) {
        String ret = "success";
        boolean accountDisabled = this.getUserSession().adminDisableUserAccount(user);
        if (!accountDisabled) {
            ret = "failure";
            this.setDisableUserAccountFailed(true);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Unable to disable account for " + user.getEmailAddress() + ".", "Administration Action"));
            return ret;
        }

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Account for " + user.getEmailAddress() + " has been disabled.", "Administration Action"));
        
        return ret;
    }

    public boolean isDisableUserAccountFailed() {
        return disableUserAccountFailed; 
    }

    public void setDisableUserAccountFailed(boolean disableUserAccountFailed) {
        this.disableUserAccountFailed = disableUserAccountFailed; 
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

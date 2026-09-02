package com.svlogic.opoppr.admin;


import com.svlogic.opoppr.annotation.CurrentUserSession;
import com.svlogic.opoppr.model.User;
import com.svlogic.opoppr.session.UserSession;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named("adminEnableUserAccount")
@RequestScoped
public class AdminEnableUserAccount{

    private UserSession userSession;
    private boolean enableUserAccountFailed;


    public AdminEnableUserAccount() {
    }   

    public String enable(User user) {
        String ret = "success";
        boolean accountEnabled = this.getUserSession().adminEnableUserAccount(user);
        if (!accountEnabled) {
            ret = "failure";
            this.setEnableUserAccountFailed(true);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Unable to enable account for " + user.getEmailAddress() + ".", "Administration Action"));
            return ret;
        }

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Account for " + user.getEmailAddress() + " has been enabled.", "Administration Action"));
        
        return ret;
    }

    public boolean isEnableUserAccountFailed() {
        return enableUserAccountFailed; 
    }

    public void setEnableUserAccountFailed(boolean enableUserAccountFailed) {
        this.enableUserAccountFailed = enableUserAccountFailed; 
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

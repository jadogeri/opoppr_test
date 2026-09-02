package com.svlogic.opoppr.admin;

import com.svlogic.opoppr.UserProfile;
import com.svlogic.opoppr.annotation.CurrentUserSession;
import com.svlogic.opoppr.model.User;
import com.svlogic.opoppr.session.UserSession;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named("adminUserProfile")
@SessionScoped
public class AdminUserProfile extends UserProfile{
    private boolean adminUpdateProfileFailed;
    private UserSession userSession;

    public AdminUserProfile() {
        super();
    }  
    
    public void adminUpdateProfile(Integer userId, User updatedUser) {        
        boolean isSuccess = this.getUserSession().updateProfile(userId, updatedUser) ;
    
        if (!isSuccess) {
           this.setAdminUpdateProfileFailed(true);
            
        } else {
            this.setAdminUpdateProfileFailed(false);
        }
    }

    public void setAdminUpdateProfileFailed(boolean adminUpdateProfileFailed) {
        this.adminUpdateProfileFailed = adminUpdateProfileFailed;
    }

    public boolean isAdminUpdateProfileFailed() {
        return this.adminUpdateProfileFailed;
    }

    public String onStatusChange(String newStatus, User user) {
        // The value is already updated in your object by the time this runs
        FacesContext context = FacesContext.getCurrentInstance();
        String ret = "success";
        boolean accountStatusChanged = false;

        switch (newStatus) {
            case "Enabled":
                accountStatusChanged = this.getUserSession().adminEnableUserAccount(user);
                if (!accountStatusChanged) {
                    ret = "failure";
                    this.setAdminUpdateProfileFailed(accountStatusChanged);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Unable to enable account for " + user.getEmailAddress() + ".", "Administration Action"));
                    return ret;
                }
                this.setAdminUpdateProfileFailed(accountStatusChanged);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Account for " + user.getEmailAddress() + " has been enabled.", "Administration Action"));
                
        return ret;                
            case "Locked":
                // Handle locked status
                accountStatusChanged = this.getUserSession().adminLockUserAccount(user);
                if (!accountStatusChanged) {
                    ret = "failure";
                    this.setAdminUpdateProfileFailed(accountStatusChanged);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Unable to lock account for " + user.getEmailAddress() + ".", "Administration Action"));
                    return ret;
                }
                this.setAdminUpdateProfileFailed(accountStatusChanged);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Account for " + user.getEmailAddress() + " has been locked.", "Administration Action"));
                return ret;

            case "Disabled":
                // Handle disabled status
                accountStatusChanged = this.getUserSession().adminDisableUserAccount(user);
                if (!accountStatusChanged) {
                    ret = "failure";
                    this.setAdminUpdateProfileFailed(accountStatusChanged);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Unable to disable account for " + user.getEmailAddress() + ".", "Administration Action"));
                    return ret;
                }
                this.setAdminUpdateProfileFailed(accountStatusChanged);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Account for " + user.getEmailAddress() + " has been disabled.", "Administration Action"));   
                return ret;
            default:
                // Handle unknown status
                this.setAdminUpdateProfileFailed(true); 
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error Occurred", "Error changing user status"));
                return "failure";
        }
    }   

    public UserSession getUserSession() {
        return userSession;
    }

    @Inject
    public void setUserSession(@CurrentUserSession UserSession userSession) {
        this.userSession = userSession;
    }
    
}

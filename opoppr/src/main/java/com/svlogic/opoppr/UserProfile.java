package com.svlogic.opoppr;

import java.io.Serializable;

import com.svlogic.opoppr.annotation.CurrentUserSession;
import com.svlogic.opoppr.model.User;
import com.svlogic.opoppr.session.UserSession;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named("userProfile")
@SessionScoped
public class UserProfile implements Serializable {
    private boolean updateProfileFailed;
    private UserSession userSession;

    public UserProfile() {} 

    public void updateProfile(Integer userId, User updateUser) {        
        boolean isSuccess = userSession.updateProfile(userId, updateUser);      
    
        if (!isSuccess) {
           FacesContext.getCurrentInstance().validationFailed();
           this.updateProfileFailed = true;
            
        } else {
            this.updateProfileFailed = false;
        }
    }

    public void setUpdateProfileFailed(boolean updateProfileFailed) {
        this.updateProfileFailed = updateProfileFailed;
    }

    public boolean isUpdateProfileFailed() {
        return this.updateProfileFailed;
    }

    public UserSession getUserSession() {
        return userSession;
    }

    @Inject
    public void setUserSession(@CurrentUserSession UserSession userSession) {
        this.userSession = userSession;
    }
}

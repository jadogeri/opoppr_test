package com.svlogic.opoppr.admin;

import java.io.Serializable;

import com.svlogic.opoppr.annotation.CurrentUserSession;
import com.svlogic.opoppr.model.Form;
import com.svlogic.opoppr.session.UserSession;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named("lat5Detail")
@SessionScoped
public class LAT5Detail implements Serializable {
    
    @Inject
    @CurrentUserSession
    private UserSession userSession;

    private Form currentForm;

    public Form getCurrentForm() {
        return currentForm;
    }

    public void setCurrentForm(Form currentForm) {
        this.currentForm = currentForm;
    }

    public LAT5Detail() {
    }
    
    @jakarta.annotation.PostConstruct
    public void init() {
        // Grabs the form we just saved in the List bean
        if (getUserSession().getCurrentForm() != null) {
            this.setCurrentForm(getUserSession().getCurrentForm());
        }
    }

    public UserSession getUserSession() {
        return userSession;
    }

}

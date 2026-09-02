package com.svlogic.opoppr;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import com.svlogic.opoppr.annotation.CurrentUserSession;
import com.svlogic.opoppr.session.UserSession;

@RequestScoped
@Named("homeAdmin")
public class HomeAdmin {
    private UserSession userSession;
    
    public HomeAdmin() {
    }

    public String flushEntityManagerCache() {
        userSession.flushEntityManagerCache();
        return null;
    }
    
    public UserSession getUserSession() {
        return userSession;
    }

    @Inject
    public void setUserSession(@CurrentUserSession UserSession userSession) {
        this.userSession = userSession;
    }
}

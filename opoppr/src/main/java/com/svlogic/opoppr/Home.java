/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.svlogic.opoppr;

import java.util.*;

import jakarta.inject.*;
import jakarta.enterprise.context.*;

import com.svlogic.opoppr.annotation.*;
import com.svlogic.opoppr.model.*;
import com.svlogic.opoppr.session.*;

/**
 *
 * @author David
 */
@Named("home")
@RequestScoped
public class Home
{
    private UserSession userSession;
    
    /**
     * Creates a new instance of Home
     */
    public Home()
    {
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
    
    public String selectForm(Form form)
    {
        userSession.setCurrentForm(form);
        return form.getFormType().getFormName();
    }
}

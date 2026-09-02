/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.svlogic.opoppr.forms.lat5;

import jakarta.enterprise.context.*;
import jakarta.inject.*;  

import com.svlogic.opoppr.annotation.*;
import com.svlogic.opoppr.session.*;

/**
 *
 * @author David
 */
@Named("validate")
@RequestScoped
public class Validate
{
    private UserSession userSession;
    
    public boolean isValid()
    {
        return userSession.validateCurrentForm();
    }

    public void setValid(boolean valid)
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
}

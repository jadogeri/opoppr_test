/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.svlogic.opoppr.forms.lat5;

import java.util.Date;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import com.svlogic.opoppr.annotation.CurrentForm;
import com.svlogic.opoppr.annotation.CurrentUserSession;
import com.svlogic.opoppr.model.Form;
import com.svlogic.opoppr.session.UserSession;

/**
 *
 * @author David
 */
@Named("lat5SignatureAndVerification")
@RequestScoped
public class SignatureAndVerification
{
    private UserSession userSession;
    private Form currentForm;
    private boolean invalidPin;
    private String pin;
    
    public UserSession getUserSession()
    {
        return userSession;
    }

    @Inject
    public void setUserSession(@CurrentUserSession UserSession userSession)
    {
        this.userSession = userSession;
    }

    public Form getCurrentForm()
    {
        return currentForm;
    }

    @Inject
    public void setCurrentForm(@CurrentForm Form currentForm)
    {
        this.currentForm = currentForm;
    }

    public boolean isInvalidPin()
    {
        return invalidPin;
    }

    public void setInvalidPin(boolean invalidPin)
    {
        this.invalidPin = invalidPin;
    }

    public String getPin()
    {
        return pin;
    }

    public void setPin(String pin)
    {
        this.pin = pin;
    }
    
    public String submit()
    {
        if (pin.equals(currentForm.getPin())) {
            getCurrentForm().getNoaPpLat5Collection().get(0).setTaxPreparerPreparedDate(new Date());
            userSession.storeBusinessInfo(getCurrentForm().getNoaPpLat5Collection().get(0));
            userSession.submitCurrentForm();
            return "success";
        }
        else {
            invalidPin = true;
            return "failure";
        }
    }
}

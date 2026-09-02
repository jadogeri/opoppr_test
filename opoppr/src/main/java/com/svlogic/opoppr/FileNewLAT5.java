/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.svlogic.opoppr;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import com.svlogic.opoppr.annotation.CurrentUserSession;
import com.svlogic.opoppr.session.UserSession;


/**
 *
 * @author David
 */
@Named("fileNewLAT5")
@RequestScoped
public class FileNewLAT5
{
    private String billNumber;
    private String PIN;
    private UserSession userSession;
    private boolean fileNewLAT5Failed;
    
    /**
     * Creates a new instance of LoginForm
     */
    public FileNewLAT5()
    {
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String username) {
        this.billNumber = username;
    }

    public String getPIN() {
        return PIN;
    }

    public void setPIN(String password) {
        this.PIN = password;
    }


    public boolean isFileNewLAT5Failed()
    {
        return fileNewLAT5Failed;
    }

    public void setFileNewLAT5Failed(boolean failed)
    {
        this.fileNewLAT5Failed = failed;
    }

    public String submit()
    {
        String ret = "success";
        fileNewLAT5Failed = !userSession.fileNewLAT5(billNumber, PIN);

        if (fileNewLAT5Failed) {
            ret = "failure";
        }
        return ret;
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

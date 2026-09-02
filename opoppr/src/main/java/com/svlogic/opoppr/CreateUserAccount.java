package com.svlogic.opoppr;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import com.svlogic.opoppr.annotation.CurrentUserSession;
import com.svlogic.opoppr.session.UserSession;

@Named("createUserAccount")
@RequestScoped
public class CreateUserAccount {
    private String username;
    private String password;
    private String fullName;
    private String phoneNumber;
    private String emailAddress;
    private boolean createUserAccountFailed;
    private UserSession userSession;
    
    public CreateUserAccount() {
    }

    public String createAccount() {
        String ret = "success";
        if (!userSession.createUserAccount(emailAddress, password, fullName, phoneNumber, emailAddress)) {
            ret = "failure";
            createUserAccountFailed = true;
        }
        return ret;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public boolean isCreateUserAccountFailed() {
        return createUserAccountFailed;
    }

    public void setCreateUserAccountFailed(boolean createUserAccountFailed) {
        this.createUserAccountFailed = createUserAccountFailed;
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

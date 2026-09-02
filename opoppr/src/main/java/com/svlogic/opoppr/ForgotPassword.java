package com.svlogic.opoppr;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import com.svlogic.opoppr.annotation.CurrentUserSession;
import com.svlogic.opoppr.session.UserSession;

@Named("forgotPassword")
@RequestScoped
public class ForgotPassword {
    private String emailAddress;
    private UserSession userSession;
    private boolean resetPasswordRequestFailed = false;

    public ForgotPassword() {
    }

    public String submitResetPasswordRequest() {
        String ret = "success";
        if (!userSession.submitResetPasswordRequest(emailAddress)) {
            resetPasswordRequestFailed = true;
            ret = "failed";
        }
        return ret;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public boolean isResetPasswordRequestFailed() {
        return resetPasswordRequestFailed;
    }

    public void setResetPasswordRequestFailed(boolean resetPasswordRequestFailed) {
        this.resetPasswordRequestFailed = resetPasswordRequestFailed;
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

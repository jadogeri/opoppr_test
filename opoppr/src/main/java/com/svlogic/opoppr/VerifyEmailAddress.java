package com.svlogic.opoppr;

import java.io.Serializable;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import com.svlogic.opoppr.annotation.CurrentUserSession;
import com.svlogic.opoppr.session.UserSession;

@Named("verifyEmailAddress")
@SessionScoped  // Has to be SessionScoped in order for the form to work properly even though this should be RequestScoped.
public class VerifyEmailAddress implements Serializable {
    private String verificationCode;
    private String password;
    private boolean verificationFailed;
    private UserSession userSession;

    public VerifyEmailAddress() {
    }

    public String verifyEmail() {
        String ret;
        if (!userSession.verifyEmailAddress(verificationCode, password)) {
            verificationFailed = true;
            ret = "failure";
        } else {
            ret = "success";
        }
        return ret;
    }

    public String resendVerificationCode() {
        userSession.resendVerificationEmail(verificationCode);
        return "resentVerificationCode";
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationId) {
        this.verificationCode = verificationId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isVerificationFailed() {
        return verificationFailed;
    }

    public void setVerificationFailed(boolean verificationFailed) {
        this.verificationFailed = verificationFailed;
    }

    public UserSession getUserSession() {
        return userSession;
    }

    @Inject
    public void setUserSession(@CurrentUserSession UserSession userSession) {
        this.userSession = userSession;
    }

    public int checkVerificationCode(String verificationCode) {
        return userSession.checkVerificationCode(verificationCode);
    }
}

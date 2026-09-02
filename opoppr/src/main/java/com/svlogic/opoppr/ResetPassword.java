package com.svlogic.opoppr;

import java.io.Serializable;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import com.svlogic.opoppr.annotation.CurrentUserSession;
import com.svlogic.opoppr.session.UserSession;

@Named("resetPassword")
@SessionScoped
public class ResetPassword implements Serializable {
    private String verificationCode;
    private String password;
    private boolean resetPasswordFailed;
    private UserSession userSession;

    public ResetPassword() {
    }

    public String resetPassword() {
        String ret;
        if (!userSession.resetPassword(verificationCode, password)) {
            resetPasswordFailed = true;
            ret = "failure";
        } else {
            ret = "success";
        }
        return ret;
    }

    public String resetProfilePassword(Integer userId) {
        String ret;
        if (!userSession.resetProfilePassword(userId, password)) {
            resetPasswordFailed = true;
            ret = "failure";
        } else {
            ret = "success";
        }
        return ret;
    }

    public int checkVerificationCode() {
        return this.userSession.checkVerificationCode(this.verificationCode);
    }

    public String resendVerificationCode() {
        userSession.resendResetPasswordEmail(verificationCode);
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

    public boolean isResetPasswordFailed() {
        return resetPasswordFailed;
    }

    public void setResetPasswordFailed(boolean verificationFailed) {
        this.resetPasswordFailed = verificationFailed;
    }

    public UserSession getUserSession() {
        return userSession;
    }

    @Inject
    public void setUserSession(@CurrentUserSession UserSession userSession) {
        this.userSession = userSession;
    }
}

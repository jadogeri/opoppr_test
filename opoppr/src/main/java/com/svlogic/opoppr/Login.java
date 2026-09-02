package com.svlogic.opoppr;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import com.svlogic.opoppr.annotation.CurrentUserSession;
import com.svlogic.opoppr.session.LoginResult;
import com.svlogic.opoppr.session.UserSession;

/**
 *
 * @author David
 */
@Named("login")
@RequestScoped
public class Login {
    private String username;
    private String password;
    private UserSession userSession;
    private boolean loginFailed;
    private LoginResult loginResult;

    /**
     * Creates a new instance of LoginForm
     */
    public Login() {
    }

    public String login() {
        String ret = "";
        loginResult = userSession.login(username, password);
        loginFailed = loginResult != LoginResult.SUCCESS;

        if (loginFailed) {
            ret = "failure";
        } else if (!userSession.isUserEnabled()) {
            userSession.logout();
            ret = "userDisabled";
        } else {
            ret = userSession.isAdmin() || userSession.isSuper() ? "successAdmin" : "success";
        }

        return ret;
    }

    public String logout() {
        userSession.logout();
        return "logout";
    }

    public String createUserAccount() {
        return "createUserAccount";
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

    public boolean isLoginFailed() {
        return loginFailed;
    }

    public void setLoginFailed(boolean failed) {
        this.loginFailed = failed;
    }

    public UserSession getUserSession() {
        return userSession;
    }

    @Inject
    public void setUserSession(@CurrentUserSession UserSession userSession) {
        this.userSession = userSession;
    }

    public String getLoginFailedReason() {
        if (loginResult == LoginResult.FAILED) {
            return "You have entered an invalid Username or Password.";
        } else if (loginResult == LoginResult.LOCKED) {
            return "Your account has been locked due to too many failed login attempts. Please use the 'Forgot Password' link to reset your password.";
        } else {
            return "";
        }
    }
}

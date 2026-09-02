package com.svlogic.opoppr.admin;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;

import com.svlogic.opoppr.annotation.CurrentUserSession;
import com.svlogic.opoppr.app.AppListener;
import com.svlogic.opoppr.controllers.UserJpaController;
import com.svlogic.opoppr.model.User;
import com.svlogic.opoppr.session.UserSession;

@Named("changeSystemAccountPassword")
@SessionScoped
public class ChangeSystemAccountPassword implements Serializable {
    private String username;
    private String password;
    private boolean changeSystemAccountPasswordFailed;
    private UserSession userSession;
    private EntityManager entityManager;

    public ChangeSystemAccountPassword() {
        this.entityManager = AppListener.getEntityManagerFactory().createEntityManager();
    }

    public String changePassword() {
        String ret;
        UserJpaController userJpaController = new UserJpaController(entityManager.getEntityManagerFactory());
        User user = userJpaController.findUserByUsername(username);
        if (user != null) {
            user.setPassword(AppListener.hashPassword(password));
            userJpaController.edit(user);
            ret = "success";
        } else {
            changeSystemAccountPasswordFailed = true;
            ret = "failure";
        }
        return ret;
    }

    public Collection<String> getSystemUsers() {
        return Arrays.asList("OPAADMIN", "OPATESTER", "OPASUPER");
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

    public boolean getChangeSystemAccountPasswordFailed() {
        return changeSystemAccountPasswordFailed;
    }

    public void setChangeSystemAccountPasswordFailed(boolean changeSystemAccountPasswordFailed) {
        this.changeSystemAccountPasswordFailed = changeSystemAccountPasswordFailed;
    }

    public UserSession getUserSession() {
        return userSession;
    }

    @Inject
    public void setUserSession(@CurrentUserSession UserSession userSession) {
        this.userSession = userSession;
    }
}

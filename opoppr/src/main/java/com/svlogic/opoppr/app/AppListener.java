/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.svlogic.opoppr.app;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import com.password4j.Password;
import com.svlogic.opoppr.controllers.UserJpaController;
import com.svlogic.opoppr.model.User;
import com.svlogic.opoppr.model.UserRole;
import com.svlogic.opoppr.model.UserStatus;

/**
 * Web application lifecycle listener.
 *
 * @author David
 */
@WebListener()
public class AppListener implements ServletContextListener {
    static private EntityManagerFactory entityManagerFactory;

    static public String hashPassword(String clearTextPassword) {
        return Password.hash(clearTextPassword).withArgon2().getResult();
    }

    static public boolean checkPassword(String clearTextPassword, String hashedPassword) {
        return Password.check(clearTextPassword, hashedPassword).withArgon2();
    }
        
    static public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        entityManagerFactory = Persistence.createEntityManagerFactory("opopprPU");

        setupSystemAccounts();
        hashExistingAccounts();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // throw new UnsupportedOperationException("Not supported yet.");
    }

    private void setupSystemAccounts() {
        EntityManager em = entityManagerFactory.createEntityManager();
        UserJpaController userJpaController = new UserJpaController(em.getEntityManagerFactory());
        if (userJpaController.findUserByUsername("OPAADMIN") == null) {
            User userOPAADMIN = new User();
            userOPAADMIN.setUsername("OPAADMIN");
            userOPAADMIN.setEmailAddress("opaadmin@sample.com");
            userOPAADMIN.setFullName("OPA Administrator");
            userOPAADMIN.setPassword(hashPassword("opoppr2025"));
            userOPAADMIN.setUserRoleId(UserRole.ADMINISTRATOR);
            userOPAADMIN.setUserStatus(UserStatus.ENABLED);
            userOPAADMIN.setPhoneNumber("5048888888");
            userJpaController.create(userOPAADMIN);
        }

        if (userJpaController.findUserByUsername("OPATESTER") == null) {
            User userOPATESTER = new User();
            userOPATESTER.setUsername("OPATESTER");
            userOPATESTER.setEmailAddress("opatester@sample.com");
            userOPATESTER.setFullName("OPA Tester");
            userOPATESTER.setPassword(hashPassword("opoppr2025"));
            userOPATESTER.setUserRoleId(UserRole.TAX_PREPARER);
            userOPATESTER.setUserStatus(UserStatus.ENABLED);
            userOPATESTER.setPhoneNumber("5048888888");
            userJpaController.create(userOPATESTER);
        }

        if (userJpaController.findUserByUsername("OPASUPER") == null) {
            User userOPASUPER = new User();
            userOPASUPER.setUsername("OPASUPER");
            userOPASUPER.setEmailAddress("opasuper@sample.com");
            userOPASUPER.setFullName("OPA Super User");
            userOPASUPER.setPassword(hashPassword("opoppr2025"));
            userOPASUPER.setUserRoleId(UserRole.SUPERUSER);
            userOPASUPER.setUserStatus(UserStatus.ENABLED);
            userOPASUPER.setPhoneNumber("5048888888");
            userJpaController.create(userOPASUPER);
        }
    }

    private void hashExistingAccounts() {
        EntityManager em = entityManagerFactory.createEntityManager();
        UserJpaController userJpaController = new UserJpaController(em.getEntityManagerFactory());
        for (User user : userJpaController.findUserEntities()) {
            if (!user.getPassword().startsWith("$argon")) {
                user.setPassword(hashPassword(user.getPassword()));
                userJpaController.edit(user);
            }
        }
    }
}

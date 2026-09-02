/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.svlogic.opoppr.admin;

import java.io.*;
import java.util.Collection;

import jakarta.inject.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.enterprise.context.*;

import org.primefaces.event.*;
import org.primefaces.model.*;

import com.svlogic.opoppr.annotation.*;
import com.svlogic.opoppr.app.AppListener;
import com.svlogic.opoppr.model.*;
import com.svlogic.opoppr.session.*;

/**
 *
 * @author david
 */
@Named("resetFormStatus")
@RequestScoped
public class ResetFormStatus {
    private EntityManager entityManager;
    private String billNumber;
    private int filingYear;
    private boolean failed;
    private UserSession userSession;

    /**
     * Creates a new instance of Initialize
     */
    public ResetFormStatus() {
        this.entityManager = AppListener.getEntityManagerFactory().createEntityManager();
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public int getFilingYear() {
        return filingYear;
    }

    public void setFilingYear(int filingYear) {
        this.filingYear = filingYear;
    }

    public String submit() {
        String ret;
        Form form = getUserSession().getForm(getBillNumber(), getFilingYear());
        if (form != null) {
            userSession.setCurrentForm(form);
            ret = "success";
        } else {
            failed = true;
            ret = "failure";
        }

        return ret;
    }

    public String confirmReset() {
        userSession.unsubmitCurrentForm();
        return "success";
    }

    public UserSession getUserSession() {
        return userSession;
    }

    @Inject
    public void setUserSession(@CurrentUserSession UserSession userSession) {
        this.userSession = userSession;
    }

    public boolean isFailed() {
        return failed;
    }

    public void setFailed(boolean failed) {
        this.failed = failed;
    }

    @SuppressWarnings("unchecked")
    public Collection<String> getFilingYears() {
        EntityManager em = getEntityManager();
        CriteriaQuery<String> cq = em.getCriteriaBuilder().createQuery(String.class);
        Root<Form> rt = cq.from(Form.class);
        cq.select(rt.get("filingYear")).distinct(true);
        cq.orderBy(em.getCriteriaBuilder().desc(rt.get("filingYear")) );
        Query q = em.createQuery(cq);
        return q.getResultList();
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }
    
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}

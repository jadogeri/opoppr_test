package com.svlogic.opoppr.forms.lat5;

import java.util.Collection;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import com.svlogic.opoppr.annotation.CurrentForm;
import com.svlogic.opoppr.annotation.CurrentUserSession;
import com.svlogic.opoppr.app.AppListener;
import com.svlogic.opoppr.controllers.BusinessTypeJpaController;
import com.svlogic.opoppr.model.BusinessType;
import com.svlogic.opoppr.model.Form;
import com.svlogic.opoppr.session.UserSession;

/**
 *
 * @author David
 */
@Named("lat5Header")
@RequestScoped
public class Header {
    private UserSession userSession;
    private Form currentForm;

    public Header() {
    }

    public String next() {
        userSession.storeBusinessInfo(getCurrentForm().getNoaPpLat5Collection().get(0));
        return "next";
    }

    public UserSession getUserSession() {
        return userSession;
    }

    @Inject
    public void setUserSession(@CurrentUserSession UserSession userSession) {
        this.userSession = userSession;
    }

    public Form getCurrentForm() {
        return currentForm;
    }

    @Inject
    public void setCurrentForm(@CurrentForm Form currentForm) {
        this.currentForm = currentForm;
    }

    public String getBusinessTypeId() {
        BusinessType businessType = getCurrentForm().getNoaPpLat5Collection().get(0).getBusinessType();
        return businessType == null ? "" : businessType.getBusinessTypeId().toString();
    }

    public void setBusinessTypeId(String businessTypeId) {
        getCurrentForm().getNoaPpLat5Collection().get(0).setBusinessType(
                new BusinessTypeJpaController(AppListener.getEntityManagerFactory()).findBusinessType(Integer.valueOf(businessTypeId)));
    }

    public Collection<BusinessType> getBusinessTypes() {
        return new BusinessTypeJpaController(AppListener.getEntityManagerFactory()).findBusinessTypeEntities();
    }
}

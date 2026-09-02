package com.svlogic.opoppr.session;
import java.io.IOException;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

/**
 *
 * @author Joseph Adogeri
 */
@Named("globalSession")
@RequestScoped
public class GlobalSession {

    public void logoutAndRedirect() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().invalidateSession(); // Invalidate the HTTP session
        // Redirect to the login page
        context.getExternalContext().redirect(context.getExternalContext().getRequestContextPath() + "/login.xhtml");
    }
}

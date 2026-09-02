/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.svlogic.opoppr.util;

import java.text.*;
import java.util.*;

import jakarta.faces.application.*;
import jakarta.faces.component.*;
import jakarta.faces.context.*;

/**
 *
 * @author David
 */
public class Messages
{
    static public FacesMessage getMessage(String defaultBundle, String resourceId, Object[] params, FacesMessage.Severity severity)
    {
        FacesContext context = FacesContext.getCurrentInstance();
        String appBundle = getAppBundle(context);
        Locale locale = getLocale(context);
        ClassLoader classLoader = getClassLoader();
        
        String summary = getString(appBundle, defaultBundle, locale, classLoader, resourceId, params);
        if (summary == null) {
            summary = "???" + resourceId + "???";
        }
        
        String detail = getString(appBundle, defaultBundle, locale, classLoader, resourceId + "_detail", params);
        
        return new FacesMessage(severity, summary, detail);
    }
    
    static public String getString(String defaultBundle, String resourceId, Object[] params)
    {
        FacesContext context = FacesContext.getCurrentInstance();
        String appBundle = getAppBundle(context);
        Locale locale = getLocale(context);
        ClassLoader classLoader = getClassLoader();
        return getString(appBundle, defaultBundle, locale, classLoader, resourceId, params);
    }
    
    static public String getAppBundle(FacesContext context)
    {
        return context.getApplication().getMessageBundle();
    }
    
    static public Locale getLocale(FacesContext context)
    {
        UIViewRoot viewRoot = context.getViewRoot();
        return viewRoot.getLocale();
    }
    
    static private ClassLoader getClassLoader()
    {
        ClassLoader ret = Thread.currentThread().getContextClassLoader();
        if (ret == null) {
            ret = ClassLoader.getSystemClassLoader();
        }
        return ret;
    }
    
    static private String getString(String appBundle, String defaultBundle, Locale locale, ClassLoader classLoader, String resourceId, Object[] params)
    {
        String ret = null;
        String resource = null;
        if (appBundle != null && !appBundle.isEmpty()) {
            ResourceBundle resourceBundle = ResourceBundle.getBundle(appBundle, locale, classLoader);
            try {
                resource = resourceBundle.getString(resourceId);
            }
            catch (MissingResourceException mre) {
            }
        }
        
        if (resource == null) {
            ResourceBundle resourceBundle = ResourceBundle.getBundle(defaultBundle, locale, classLoader);
            try {
                resource = resourceBundle.getString(resourceId);
            }
            catch (MissingResourceException mre) {
            }
        }
    
        if (resource != null) {
            if (params == null) {
                ret = resource;
            }
            else {
                MessageFormat formatter = new MessageFormat(resource, locale);
                ret = formatter.format(params);
            }
        }
        
        return ret;
    }
}

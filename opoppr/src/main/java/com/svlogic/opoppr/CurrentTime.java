/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.svlogic.opoppr;

import java.util.*;

import jakarta.enterprise.context.*;
import jakarta.enterprise.inject.*;
import jakarta.faces.context.*;
import jakarta.inject.*;

/**
 *
 * @author David
 */
@Named("currentTime")
@ApplicationScoped
public class CurrentTime
{
    public Date getCurrentTime()
    {
        return getCalendar().getTime();
    }
    
    @Produces @Named
    public int getCurrentYear()
    {
        return getCalendar().get(Calendar.YEAR);
    }
    
    private Calendar getCalendar()
    {
        return GregorianCalendar.getInstance(FacesContext.getCurrentInstance().getViewRoot().getLocale());
    }
}

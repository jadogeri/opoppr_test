/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.svlogic.opoppr.validator;

import java.util.regex.*;

import jakarta.faces.application.*;
import jakarta.faces.component.*;
import jakarta.faces.context.*;
import jakarta.faces.validator.*;

import com.svlogic.opoppr.util.*;

/**
 *
 * @author David
 */
@FacesValidator("com.svlogic.opoppr.validator.Username")
public class UsernameValidator implements Validator
{
    private boolean notRequired;

    public boolean getNotRequired()
    {
        return notRequired;
    }

    public void setNotRequired(boolean notRequired)
    {
        this.notRequired = notRequired;
    }
    
    @Override
    public void validate(FacesContext fc, UIComponent uic, Object o) throws ValidatorException
    {
        String str = (String)o;
        Pattern p = Pattern.compile("^[A-Za-z][A-Za-z0-9_]{4,24}$" + (notRequired ? "?" : ""));
        Matcher m = p.matcher(str);
        if (!m.matches()) {
            FacesMessage msg = Messages.getMessage("com.svlogic.opoppr.Messages",
                                                   "com.svlogic.opoppr.validator.UsernameValidator",
                                                   new Object[]{uic.getAttributes().get("label")},
                                                   FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }
}

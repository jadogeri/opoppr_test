/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.svlogic.opoppr.validator;

import jakarta.faces.application.*;
import jakarta.faces.component.*;
import jakarta.faces.context.*;
import jakarta.faces.validator.*;

import com.svlogic.opoppr.util.*;

/**
 *
 * @author David
 */
@FacesValidator("com.svlogic.opoppr.validator.Equals")
public class EqualsValidator implements Validator
{
    private String to;

    public String getTo()
    {
        return to;
    }

    public void setTo(String to)
    {
        this.to = to;
    }
    
    @Override
    public void validate(FacesContext fc, UIComponent uic, Object o) throws ValidatorException
    {
        UIInput uicTo = (UIInput)uic.findComponent(to);
        if (uicTo.getLocalValue() == null && o != null
            || !uicTo.getLocalValue().equals(o)) {
            FacesMessage msg = Messages.getMessage("com.svlogic.opoppr.Messages",
                                                   "com.svlogic.opoppr.validator.EqualsValidator",
                                                   new Object[]{uicTo.getAttributes().get("label"),
                                                                uic.getAttributes().get("label")
                                                   },
                                                   FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }
}

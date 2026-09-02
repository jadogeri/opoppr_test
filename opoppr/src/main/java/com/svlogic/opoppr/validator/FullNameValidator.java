/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.svlogic.opoppr.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.svlogic.opoppr.util.Messages;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;

/**
 *
 * @author Joseph Adogeri
 */
@FacesValidator("com.svlogic.opoppr.validator.FullName")
public class FullNameValidator implements Validator
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
        Pattern p = Pattern.compile("^(?!.*\\\\s{2,})[A-Za-z ]{7,50}(?<! )(?<! )$" + (notRequired ? "?" : ""));
        Matcher m = p.matcher(str);
        if (!m.matches()) {
            FacesMessage msg = Messages.getMessage("com.svlogic.opoppr.Messages",
                                                   "com.svlogic.opoppr.validator.FullNameValidator",
                                                   new Object[]{uic.getAttributes().get("label")},
                                                   FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }
}

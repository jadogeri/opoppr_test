/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.svlogic.opoppr.converter;

import jakarta.faces.component.*;
import jakarta.faces.context.*;
import jakarta.faces.convert.*;

/**
 *
 * @author David
 */
@FacesConverter("com.svlogic.opoppr.converter.Mask")
public class MaskConverter implements Converter
{
    private String mask;

    public String getMask()
    {
        return mask;
    }

    public void setMask(String mask)
    {
        this.mask = mask;
    }
    
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string)
    {
        String decoratorChars = getDecoratorChars(mask);
        return string.replaceAll("[" + decoratorChars + "]", "");
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o)
    {
        String str = (String)o;
        StringBuilder ret = new StringBuilder();
        
        if (str != null && !str.isEmpty()) {
            int j = 0;
            for (int i = 0;  i < mask.length();  ++i) {
                if (mask.charAt(i) != '#') {
                    ret.append(mask.charAt(i));
                }
                else {
                    ret.append(str.charAt(j++));
                }
            }
        }
        
        return ret.toString();
    }
    
    private String getDecoratorChars(String mask)
    {
        return mask.replaceAll("#", "");
    }
}

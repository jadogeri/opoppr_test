package com.svlogic.opoppr.tag;

import java.io.IOException;
import java.util.Iterator;

import jakarta.faces.component.FacesComponent;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UIComponentBase;
import jakarta.faces.context.FacesContext;

/*
 * @author Brendan Healey (Oversteer)
 * 
 * Modified by David Vazquez because code had an error.
 * 
 */
@FacesComponent("com.svlogic.opoppr.tag.UIDisablePanel")
public class UIDisablePanel extends UIComponentBase
{
    private enum PropertyKeys
    {
        disabled;
    }

    public UIDisablePanel()
    {
        setRendererType(null);
    }

    @Override
    public void encodeBegin(FacesContext context) throws IOException
    {

        boolean toDisable = isDisabled();
        processDisablePanel(this, toDisable);
        //super.encodeBegin(context);
    }

    public void processDisablePanel(UIComponent root, boolean toDisable)
    {

        /*
         * The key point here is that a child component of <x:disablePanel> may
         * already be disabled, in which case we don't want to enable it if the
         * <x:disablePanel disabled= attribute is set to true.
         */

        Iterator<UIComponent> it = root.getFacetsAndChildren();
        while (it.hasNext()) {
            UIComponent c = it.next();
            if (toDisable) { // <x:disablePanel disabled="true">
                Boolean curState = (Boolean) c.getAttributes().get("disabled");
                if (curState == null || curState == false) {
                    c.getAttributes().put("UIPanelDisableFlag", true);
                    c.getAttributes().put("disabled", true);
                }
            }
            else { // <x:disablePanel disabled="false">
                if (c.getAttributes().get("UIPanelDisableFlag") != null) {
                    c.getAttributes().remove("UIPanelDisableFlag");
                    c.getAttributes().put("disabled", false);
                }
            }

            if (c.getChildCount() > 0) {
                processDisablePanel(c, toDisable);
            }
        }
    }

    @Override
    public String getFamily()
    {
        // Got to override it but it doesn't get called.
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isDisabled()
    {
        return (Boolean) getStateHelper().eval(PropertyKeys.disabled, false);
    }

    public void setDisabled(boolean disabled)
    {
        getStateHelper().put(PropertyKeys.disabled, disabled);
    }
}

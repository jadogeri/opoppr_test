package com.svlogic.opoppr.forms.lat5;

import java.io.Serializable;

import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Named;

import jakarta.faces.component.html.HtmlSelectOneMenu;

/**
 *
 * @author David
 */
@Named("lat5Section4")
@ConversationScoped
public class Section4 extends FilingForm implements Serializable {
    private HtmlSelectOneMenu propertyTypeMenu;

    /**
     * Creates a new instance of Section2
     */
    public Section4() {
        super(4, "13");
    }

    public HtmlSelectOneMenu getPropertyTypeMenu() {
        return propertyTypeMenu;
    }

    public void setPropertyTypeMenu(HtmlSelectOneMenu propertyTypeMenu) {
        this.propertyTypeMenu = propertyTypeMenu;
    }

}

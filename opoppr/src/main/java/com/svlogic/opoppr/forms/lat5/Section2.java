package com.svlogic.opoppr.forms.lat5;

import java.io.Serializable;

import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Named;

/**
 * @author David
 */
@Named("lat5Section2")
@ConversationScoped
public class Section2 extends FilingForm implements Serializable {
    /**
     * Creates a new instance of Section2
     */
    public Section2() {
        super(2, "08");
    }
}

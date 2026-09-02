package com.svlogic.opoppr.forms;

import jakarta.enterprise.context.Conversation;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;

import com.svlogic.opoppr.annotation.CurrentForm;
import com.svlogic.opoppr.annotation.CurrentUserSession;
import com.svlogic.opoppr.model.Form;
import com.svlogic.opoppr.session.UserSession;

/**
 *
 * @author David
 */
abstract public class EditableForm {
    private Conversation conversation;
    private boolean dirty;
    private Form currentForm;
    private UserSession userSession;

    public boolean isDirty() {
        return dirty;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    public Conversation getConversation() {
        return conversation;
    }

    @Inject
    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public Form getCurrentForm() {
        return currentForm;
    }

    @Inject
    public void setCurrentForm(@CurrentForm Form currentForm) {
        this.currentForm = currentForm;
    }

    public UserSession getUserSession() {
        return userSession;
    }

    @Inject
    public void setUserSession(@CurrentUserSession UserSession userSession) {
        this.userSession = userSession;
    }

    public void initConversation() {
        if (!FacesContext.getCurrentInstance().isPostback()
                && conversation.isTransient()) {
            conversation.begin();
        }
    }

    public void endConversation() {
        if (!conversation.isTransient()) {
            conversation.end();
        }
    }
}

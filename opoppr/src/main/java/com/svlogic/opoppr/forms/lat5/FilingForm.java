package com.svlogic.opoppr.forms.lat5;

import java.util.ArrayList;
import java.util.List;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import org.primefaces.PrimeFaces;
import com.svlogic.opoppr.annotation.CurrentForm;
import com.svlogic.opoppr.app.AppListener;
import com.svlogic.opoppr.controllers.PropertyAssetJpaController;
import com.svlogic.opoppr.forms.EditableForm;
import com.svlogic.opoppr.model.Form;
import com.svlogic.opoppr.model.NoaPpLat5Filing;
import com.svlogic.opoppr.model.PropertyAsset;
import com.svlogic.opoppr.util.Messages;

abstract public class FilingForm extends EditableForm {
    private int sectionNumber;
    private String category;
    private List<Filing> filings = new ArrayList<>();
    private List<PropertyAsset> propertyAssets;
    
    // Tracks whether row errors should show on screen (keeps cells clean initially)
    private boolean showErrors = false;

    public FilingForm() {}

    public FilingForm(int sectionNumber, String category) {
        this.sectionNumber = sectionNumber;
        this.category = category;
    }

    public boolean isShowErrors() { return showErrors; }
    public void setShowErrors(boolean showErrors) { this.showErrors = showErrors; }
    public List<Filing> getFilings() { return filings; }
    public void setFilings(List<Filing> filings) { this.filings = filings; }
    public List<PropertyAsset> getPropertyAssets() { return propertyAssets; }
    public void setPropertyAssets(List<PropertyAsset> propertyAssets) { this.propertyAssets = propertyAssets; }

    public List<String> getPropertyAssetDescriptions() {
        List<String> descriptions = new ArrayList<>();
        descriptions.add("Delete Row");
        if (propertyAssets != null) {
            propertyAssets.stream()
                    .map(PropertyAsset::getAssetDescription)
                    .forEach(descriptions::add);
        }
        return descriptions;
    }

    @Inject
    public void setCurrentForm(@CurrentForm Form currentForm) {
        super.setCurrentForm(currentForm);
        initializeFilingRows();
    }

    private void initializeFilingRows() {
        propertyAssets = new PropertyAssetJpaController(AppListener.getEntityManagerFactory())
                .findPropertyAssetBySectionNumber(this.sectionNumber);
        propertyAssets.sort((p1, p2) -> p1.getAssetDescription().compareTo(p2.getAssetDescription()));
        getUserSession().getCurrentFormFilingsWithCategory(this.category).forEach(f -> {
            Filing filing = new Filing(propertyAssets);
            filing.setNoaPpLat5Filing(f);
            filings.add(filing);
        });

        while (filings.size() < 16) {
            addNewRow();
        }
        
        // Ensure errors stay hidden when the sheet initializes or loads a clean record sets
        this.showErrors = false;
    }

    public String addNewRow() {
        NoaPpLat5Filing f = new NoaPpLat5Filing();
        Filing filing = new Filing(propertyAssets);
        filing.setNoaPpLat5Filing(f);
        filings.add(filing);
        setDirty(true);
        PrimeFaces.current().ajax().addCallbackParam("newRowIndex", filings.size() - 1);
        return null;
    }

    public String upload() { return null; }

    public void saveFilings() {
        getUserSession().storeFilings(getAddUpdateFilings());
        getUserSession().deleteFilings(getDeleteFilings());
        setDirty(false);
    }

    public String next() {
        if (!validateFilings()) {
            return null;
        }
<<<<<<< HEAD
        this.showErrors = false;
        getUserSession().storeFilings(getAddUpdateFilings());
        getUserSession().deleteFilings(getDeleteFilings());
        setDirty(false);
=======
        saveFilings();
>>>>>>> 8af8911a3e3abc61567b8985ca9d13072c1661e4
        endConversation();
        return "next";
    }

    public String previous() {
        if (!validateFilings()) {
            return null;
        }
<<<<<<< HEAD
        this.showErrors = false;
        getUserSession().storeFilings(getAddUpdateFilings());
        getUserSession().deleteFilings(getDeleteFilings());
        setDirty(false);
=======
        saveFilings();
>>>>>>> 8af8911a3e3abc61567b8985ca9d13072c1661e4
        endConversation();
        return "previous";
    }

    protected boolean validateFilings() {
        if (!filings.stream().allMatch(f -> f.isValid(this.category))) {
            // Activate the view state flag so the layout updates to show error blocks
            this.showErrors = true;
            
            FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage msg = Messages.getMessage("com.svlogic.opoppr.forms.Messages",
                    "com.svlogic.opoppr.validator.FilingsValidator",
                    null,
                    FacesMessage.SEVERITY_ERROR);
            fc.addMessage("filings", msg);
            
            // Programmatically invoke an internal update loop to display error states
            PrimeFaces.current().ajax().update("form:filings", "form:messages");
            return false;
        }
        return true;
    }

    public Long getGrandTotal() {
        return filings.stream()
                .mapToLong(f -> f.getNoaPpLat5Filing().getAcquisitionCost() == null ? 0 : f.getNoaPpLat5Filing().getAcquisitionCost())
                .sum();
    }

    protected List<NoaPpLat5Filing> getAddUpdateFilings() {
        return filings.stream()
                .filter(f -> !f.getPptype().isEmpty() || (f.getNoaPpLat5Filing().getCategory() != null
                        && f.getNoaPpLat5Filing().getCategory().equals("99")))
                .map(Filing::getNoaPpLat5Filing)
                .collect(java.util.stream.Collectors.toList());
    }

    protected List<NoaPpLat5Filing> getDeleteFilings() {
        return filings.stream()
                .filter(f -> f.getPptype().isEmpty() && f.getNoaPpLat5Filing().getNoaPpLat5FilingId() != null)
                .map(Filing::getNoaPpLat5Filing)
                .collect(java.util.stream.Collectors.toList());
    }
}

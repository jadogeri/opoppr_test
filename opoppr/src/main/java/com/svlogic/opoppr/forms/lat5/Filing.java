/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.svlogic.opoppr.forms.lat5;

import java.io.Serializable;
import java.util.Collection;
import java.util.UUID;

import com.svlogic.opoppr.model.NoaPpLat5Filing;
import com.svlogic.opoppr.model.PropertyAsset;

/**
 *
 * @author David
 */
public class Filing implements Serializable {
    private String pptype = "";
    private NoaPpLat5Filing noaPpLat5Filing;
    private Collection<PropertyAsset> propertyAssets;
    private boolean valid = true;
    private final String sheetRowKey = UUID.randomUUID().toString();

    public Filing(Collection<PropertyAsset> propertyAssets) {
        this.propertyAssets = propertyAssets;
    }

    public Filing(Collection<PropertyAsset> propertyAssets, String pptype) {
        this.propertyAssets = propertyAssets;
        this.pptype = pptype;
    }

    public String getPptype() {
        return pptype;
    }

    public void setPptype(String pptype) {
        this.pptype = pptype == null ? "" : pptype;
        if (this.pptype.isEmpty()) {
            getNoaPpLat5Filing().setPropertyAsset(null);
            return;
        }
        propertyAssets.stream()
                .filter(pa -> pa.getPptype().equals(this.pptype))
                .findFirst()
                .ifPresent(pa -> getNoaPpLat5Filing().setPropertyAsset(pa));
    }

    public NoaPpLat5Filing getNoaPpLat5Filing() {
        return noaPpLat5Filing;
    }

    public void setNoaPpLat5Filing(NoaPpLat5Filing noaPpLat5Filing) {
        this.noaPpLat5Filing = noaPpLat5Filing;
        if (noaPpLat5Filing.getPropertyAsset() != null) {
            pptype = noaPpLat5Filing.getPropertyAsset().getPptype();
        }
    }

    public String getPropertyAssetDescription() {
        if (pptype.isEmpty() || propertyAssets == null) {
            return "";
        }
        return propertyAssets.stream()
                .filter(pa -> pa.getPptype().equals(pptype))
                .map(PropertyAsset::getAssetDescription)
                .findFirst()
                .orElse("");
    }

    public void setPropertyAssetDescription(String description) {
        if (description == null || description.isEmpty() || "Delete Row".equals(description)) {
            setPptype("");
            return;
        }
        propertyAssets.stream()
                .filter(pa -> description.equals(pa.getAssetDescription()))
                .map(PropertyAsset::getPptype)
                .findFirst()
                .ifPresent(this::setPptype);
    }

    public String getSheetRowKey() {
        return sheetRowKey;
    }

    public boolean isValid(String category) {
        switch (category) {
            case "08":
            case "10":
                this.valid = pptype.isEmpty()
                        || (getNoaPpLat5Filing().getAcquisitionCost() != null
                                && getNoaPpLat5Filing().getYracqd() != null);
                break;

            case "13":
                this.valid = pptype.isEmpty()
                        || ((!pptype.equals("0") ||
                                (getNoaPpLat5Filing().getItemDescription() != null
                                        && !getNoaPpLat5Filing().getItemDescription().isEmpty()))
                                && getNoaPpLat5Filing().getAcquisitionCost() != null
                                && getNoaPpLat5Filing().getYracqd() != null);
                break;

            case "99":
                this.valid = (getNoaPpLat5Filing().getItemDescription() == null
                        || getNoaPpLat5Filing().getItemDescription().isEmpty())
                        || ((getNoaPpLat5Filing().getConsignerOwnerName() != null
                                && !getNoaPpLat5Filing().getConsignerOwnerName().isEmpty())
                                && (getNoaPpLat5Filing().getConsignerMailingAddr() != null
                                        && !getNoaPpLat5Filing().getConsignerMailingAddr().isEmpty())
                                && (getNoaPpLat5Filing().getConsignerTelNo() != null
                                        && !getNoaPpLat5Filing().getConsignerTelNo().isEmpty())
                                && (getNoaPpLat5Filing().getEffectiveLife() != null
                                        && getNoaPpLat5Filing().getEffectiveLife() >= 0)
                                && (getNoaPpLat5Filing().getConsignerRentalAmt() != null
                                        && getNoaPpLat5Filing().getConsignerRentalAmt() >= 0));
                break;
        }

        return this.valid;
    }
}

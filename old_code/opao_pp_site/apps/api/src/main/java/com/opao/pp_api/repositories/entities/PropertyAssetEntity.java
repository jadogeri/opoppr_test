package com.opao.pp_api.repositories.entities;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;

import com.opao.pp_api.common.constants.models.PropertyAssetConstants;

@Entity
@Data // Generates Getters, Setters, toString, equals, and hashCode
@NoArgsConstructor // Generates a public no-argument constructor
@AllArgsConstructor // Generates a constructor with all fields
@Table(name = "property_asset")
public class PropertyAssetEntity implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PROPERTY_ASSET_ID")
    private Integer propertyAssetId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECTION_NUMBER")
    private int sectionNumber;
    @Basic(optional = false)
    @NotNull
    @Size(min = PropertyAssetConstants.CATEGORY_MIN_LENGTH, max = PropertyAssetConstants.CATEGORY_MAX_LENGTH)
    @Column(name = "CATEGORY")
    private String category;
    @Basic(optional = false)
    @NotNull
    @Size(min = PropertyAssetConstants.PPTYPE_MIN_LENGTH, max = PropertyAssetConstants.PPTYPE_MAX_LENGTH)
    @Column(name = "PPTYPE")
    private String pptype;
    @Basic(optional = false)
    @NotNull
    @Size(min = PropertyAssetConstants.ASSET_DESCRIPTION_MIN_LENGTH, max = PropertyAssetConstants.ASSET_DESCRIPTION_MAX_LENGTH)
    @Column(name = "ASSET_DESCRIPTION")
    private String assetDescription;
    @Basic(optional = false)
    @NotNull
    @Column(name = "EFFECTIVE_LIFE")
    private int effectiveLife;

    public PropertyAssetEntity(Integer propertyAssetId)
    {
        this.propertyAssetId = propertyAssetId;
    }
       
    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (propertyAssetId != null ? propertyAssetId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PropertyAssetEntity)) {
            return false;
        }
        PropertyAssetEntity other = (PropertyAssetEntity) object;
        if ((this.propertyAssetId == null && other.propertyAssetId != null) || (this.propertyAssetId != null && !this.propertyAssetId.equals(other.propertyAssetId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "com.opao.pp_api.repositories.entities.PropertyAssetEntity[ propertyAssetId=" + propertyAssetId + " ]";
    }

    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.svlogic.opoppr.model;

import java.io.*;
import java.util.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import jakarta.xml.bind.annotation.*;

/**
 *
 * @author David
 */
@Entity
@Table(name = "property_asset")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PropertyAsset.findAll", query = "SELECT p FROM PropertyAsset p"),
    @NamedQuery(name = "PropertyAsset.findByPropertyAssetId", query = "SELECT p FROM PropertyAsset p WHERE p.propertyAssetId = :propertyAssetId"),
    @NamedQuery(name = "PropertyAsset.findBySectionNumber", query = "SELECT p FROM PropertyAsset p WHERE p.sectionNumber = :sectionNumber"),
    @NamedQuery(name = "PropertyAsset.findBySectionNumberOrderByPpType", query = "SELECT p FROM PropertyAsset p WHERE p.sectionNumber = :sectionNumber ORDER BY p.propertyAssetId"),
    @NamedQuery(name = "PropertyAsset.findByCategory", query = "SELECT p FROM PropertyAsset p WHERE p.category = :category"),
    @NamedQuery(name = "PropertyAsset.findByPptype", query = "SELECT p FROM PropertyAsset p WHERE p.pptype = :pptype"),
    @NamedQuery(name = "PropertyAsset.findByAssetDescription", query = "SELECT p FROM PropertyAsset p WHERE p.assetDescription = :assetDescription"),
    @NamedQuery(name = "PropertyAsset.findByEffectiveLife", query = "SELECT p FROM PropertyAsset p WHERE p.effectiveLife = :effectiveLife")})
public class PropertyAsset implements Serializable
{
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
    @Size(min = 1, max = 5)
    @Column(name = "CATEGORY")
    private String category;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "PPTYPE")
    private String pptype;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "ASSET_DESCRIPTION")
    private String assetDescription;
    @Basic(optional = false)
    @NotNull
    @Column(name = "EFFECTIVE_LIFE")
    private int effectiveLife;

    public PropertyAsset()
    {
    }

    public PropertyAsset(Integer propertyAssetId)
    {
        this.propertyAssetId = propertyAssetId;
    }

    public PropertyAsset(Integer propertyAssetId, int sectionNumber, String category, String pptype, String assetDescription, int effectiveLife)
    {
        this.propertyAssetId = propertyAssetId;
        this.sectionNumber = sectionNumber;
        this.category = category;
        this.pptype = pptype;
        this.assetDescription = assetDescription;
        this.effectiveLife = effectiveLife;
    }

    public Integer getPropertyAssetId()
    {
        return propertyAssetId;
    }

    public void setPropertyAssetId(Integer propertyAssetId)
    {
        this.propertyAssetId = propertyAssetId;
    }

    public int getSectionNumber()
    {
        return sectionNumber;
    }

    public void setSectionNumber(int sectionNumber)
    {
        this.sectionNumber = sectionNumber;
    }

    public String getCategory()
    {
        return category;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }

    public String getPptype()
    {
        return pptype;
    }

    public void setPptype(String pptype)
    {
        this.pptype = pptype;
    }

    public String getAssetDescription()
    {
        return assetDescription;
    }

    public void setAssetDescription(String assetDescription)
    {
        this.assetDescription = assetDescription;
    }

    public int getEffectiveLife()
    {
        return effectiveLife;
    }

    public void setEffectiveLife(int effectiveLife)
    {
        this.effectiveLife = effectiveLife;
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
        if (!(object instanceof PropertyAsset)) {
            return false;
        }
        PropertyAsset other = (PropertyAsset) object;
        if ((this.propertyAssetId == null && other.propertyAssetId != null) || (this.propertyAssetId != null && !this.propertyAssetId.equals(other.propertyAssetId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "com.svlogic.opoppr.model.PropertyAsset[ propertyAssetId=" + propertyAssetId + " ]";
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.svlogic.opoppr.model;

import java.io.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

/**
 *
 * @author David
 */
@Entity
@Table(name = "noa_pp_lat_5_filing")
@NamedQueries({
    @NamedQuery(name = "NoaPpLat5Filing.findAll", query = "SELECT n FROM NoaPpLat5Filing n"),
    @NamedQuery(name = "NoaPpLat5Filing.findByNoaPpLat5FilingId", query = "SELECT n FROM NoaPpLat5Filing n WHERE n.noaPpLat5FilingId = :noaPpLat5FilingId"),
    @NamedQuery(name = "NoaPpLat5Filing.findByJur", query = "SELECT n FROM NoaPpLat5Filing n WHERE n.jur = :jur"),
    @NamedQuery(name = "NoaPpLat5Filing.findByParid", query = "SELECT n FROM NoaPpLat5Filing n WHERE n.parid = :parid"),
    @NamedQuery(name = "NoaPpLat5Filing.findByTaxyr", query = "SELECT n FROM NoaPpLat5Filing n WHERE n.taxyr = :taxyr"),
    @NamedQuery(name = "NoaPpLat5Filing.findByCategory", query = "SELECT n FROM NoaPpLat5Filing n WHERE n.category = :category"),
    @NamedQuery(name = "NoaPpLat5Filing.findByPptype", query = "SELECT n FROM NoaPpLat5Filing n WHERE n.pptype = :pptype"),
    @NamedQuery(name = "NoaPpLat5Filing.findByFileyr", query = "SELECT n FROM NoaPpLat5Filing n WHERE n.fileyr = :fileyr"),
    @NamedQuery(name = "NoaPpLat5Filing.findByYracqd", query = "SELECT n FROM NoaPpLat5Filing n WHERE n.yracqd = :yracqd"),
    @NamedQuery(name = "NoaPpLat5Filing.findByNounits", query = "SELECT n FROM NoaPpLat5Filing n WHERE n.nounits = :nounits"),
    @NamedQuery(name = "NoaPpLat5Filing.findByAcquisitionCost", query = "SELECT n FROM NoaPpLat5Filing n WHERE n.acquisitionCost = :acquisitionCost"),
    @NamedQuery(name = "NoaPpLat5Filing.findByEffectiveLife", query = "SELECT n FROM NoaPpLat5Filing n WHERE n.effectiveLife = :effectiveLife"),
    @NamedQuery(name = "NoaPpLat5Filing.findByConsignerOwnerName", query = "SELECT n FROM NoaPpLat5Filing n WHERE n.consignerOwnerName = :consignerOwnerName"),
    @NamedQuery(name = "NoaPpLat5Filing.findByConsignerMailingAddr", query = "SELECT n FROM NoaPpLat5Filing n WHERE n.consignerMailingAddr = :consignerMailingAddr"),
    @NamedQuery(name = "NoaPpLat5Filing.findByConsignerRentalAmt", query = "SELECT n FROM NoaPpLat5Filing n WHERE n.consignerRentalAmt = :consignerRentalAmt"),
    @NamedQuery(name = "NoaPpLat5Filing.findByItemDescription", query = "SELECT n FROM NoaPpLat5Filing n WHERE n.itemDescription = :itemDescription"),
    @NamedQuery(name = "NoaPpLat5Filing.findByConsignerTelNo", query = "SELECT n FROM NoaPpLat5Filing n WHERE n.consignerTelNo = :consignerTelNo")})
public class NoaPpLat5Filing implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "NOA_PP_LAT_5_FILING_ID")
    private Integer noaPpLat5FilingId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "JUR")
    private String jur;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "PARID")
    private String parid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TAXYR")
    private int taxyr;
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
    @Column(name = "FILEYR")
    private int fileyr;
    @Column(name = "YRACQD")
    private Integer yracqd;
    @Column(name = "NOUNITS")
    private Integer nounits = 1;
    @Column(name = "ACQUISITION_COST")
    private Long acquisitionCost;
    @Column(name = "EFFECTIVE_LIFE")
    private Integer effectiveLife = 0;
    @Size(max = 50)
    @Column(name = "CONSIGNER_OWNER_NAME")
    private String consignerOwnerName;
    @Size(max = 50)
    @Column(name = "CONSIGNER_MAILING_ADDR")
    private String consignerMailingAddr;
    @Column(name = "CONSIGNER_RENTAL_AMT")
    private Long consignerRentalAmt;
    @Size(max = 50)
    @Column(name = "ITEM_DESCRIPTION")
    private String itemDescription;
    @Size(max = 10)
    @Column(name = "CONSIGNER_TEL_NO")
    private String consignerTelNo;
    @JoinColumn(name = "NOA_PP_LAT_5_ID", referencedColumnName = "NOA_PP_LAT_5_ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private NoaPpLat5 noaPpLat5;
    @JoinColumn(name = "PROPERTY_ASSET_ID", referencedColumnName = "PROPERTY_ASSET_ID")
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private PropertyAsset propertyAsset;
    
    public NoaPpLat5Filing()
    {
    }

    public NoaPpLat5Filing(Integer noaPpLat5FilingId)
    {
        this.noaPpLat5FilingId = noaPpLat5FilingId;
    }

    public NoaPpLat5Filing(Integer noaPpLat5FilingId, String jur, String parid, int taxyr, String category, String pptype, int fileyr)
    {
        this.noaPpLat5FilingId = noaPpLat5FilingId;
        this.jur = jur;
        this.parid = parid;
        this.taxyr = taxyr;
        this.category = category;
        this.pptype = pptype;
        this.fileyr = fileyr;
    }

    public Integer getNoaPpLat5FilingId()
    {
        return noaPpLat5FilingId;
    }

    public void setNoaPpLat5FilingId(Integer noaPpLat5FilingId)
    {
        this.noaPpLat5FilingId = noaPpLat5FilingId;
    }

    public String getJur()
    {
        return jur;
    }

    public void setJur(String jur)
    {
        this.jur = jur;
    }

    public String getParid()
    {
        return parid;
    }

    public void setParid(String parid)
    {
        this.parid = parid;
    }

    public int getTaxyr()
    {
        return taxyr;
    }

    public void setTaxyr(int taxyr)
    {
        this.taxyr = taxyr;
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

    public int getFileyr()
    {
        return fileyr;
    }

    public void setFileyr(int fileyr)
    {
        this.fileyr = fileyr;
    }

    public Integer getYracqd()
    {
        return yracqd;
    }

    public void setYracqd(Integer yracqd)
    {
        this.yracqd = yracqd;
    }

    public Integer getNounits()
    {
        return nounits;
    }

    public void setNounits(Integer nounits)
    {
        this.nounits = nounits;
    }

    public Long getAcquisitionCost()
    {
        return acquisitionCost;
    }

    public void setAcquisitionCost(Long acquisitionCost)
    {
        this.acquisitionCost = acquisitionCost;
    }

    public Integer getEffectiveLife()
    {
        return effectiveLife;
    }

    public void setEffectiveLife(Integer effectiveLife)
    {
        this.effectiveLife = effectiveLife;
    }

    public String getConsignerOwnerName()
    {
        return consignerOwnerName;
    }

    public void setConsignerOwnerName(String consignerOwnerName)
    {
        this.consignerOwnerName = consignerOwnerName;
    }

    public String getConsignerMailingAddr()
    {
        return consignerMailingAddr;
    }

    public void setConsignerMailingAddr(String consignerMailingAddr)
    {
        this.consignerMailingAddr = consignerMailingAddr;
    }

    public Long getConsignerRentalAmt()
    {
        return consignerRentalAmt;
    }

    public void setConsignerRentalAmt(Long consignerRentalAmt)
    {
        this.consignerRentalAmt = consignerRentalAmt;
    }

    public String getItemDescription()
    {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription)
    {
        this.itemDescription = itemDescription;
    }

    public String getConsignerTelNo()
    {
        return consignerTelNo;
    }

    public void setConsignerTelNo(String consignerTelNo)
    {
        this.consignerTelNo = consignerTelNo;
    }

    public NoaPpLat5 getNoaPpLat5()
    {
        return noaPpLat5;
    }

    public void setNoaPpLat5(NoaPpLat5 noaPpLat5)
    {
        this.noaPpLat5 = noaPpLat5;
    }

    public PropertyAsset getPropertyAsset()
    {
        return propertyAsset;
    }

    public void setPropertyAsset(PropertyAsset propertyAsset)
    {
        this.propertyAsset = propertyAsset;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (noaPpLat5FilingId != null ? noaPpLat5FilingId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NoaPpLat5Filing)) {
            return false;
        }
        NoaPpLat5Filing other = (NoaPpLat5Filing) object;
        if ((this.noaPpLat5FilingId == null && other.noaPpLat5FilingId != null) || (this.noaPpLat5FilingId != null && !this.noaPpLat5FilingId.equals(other.noaPpLat5FilingId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "com.svlogic.opoppr.model.NoaPpLat5Filing[ noaPpLat5FilingId=" + noaPpLat5FilingId + " ]";
    }
    
}

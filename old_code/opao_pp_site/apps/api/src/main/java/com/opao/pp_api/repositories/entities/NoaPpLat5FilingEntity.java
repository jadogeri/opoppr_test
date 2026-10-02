package com.opao.pp_api.repositories.entities;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.io.Serializable;

import com.opao.pp_api.common.constants.models.NoaPpLat5FilingConstants;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
 
@Entity
@Data // Generates Getters, Setters, toString, equals, and hashCode
@NoArgsConstructor // Generates a public no-argument constructor
@AllArgsConstructor // Generates a constructor with all fields
@Table(name = "noa_pp_lat5_filing")     
public class NoaPpLat5FilingEntity implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "NOA_PP_LAT_5_FILING_ID")
    private Integer noaPpLat5FilingId;
    @Basic(optional = false)
    @NotNull
    @Size(min = NoaPpLat5FilingConstants.JUR_MIN_LENGTH, max = NoaPpLat5FilingConstants.JUR_MAX_LENGTH)
    @Column(name = "JUR")
    private String jur;
    @Basic(optional = false)
    @NotNull
    @Size(min = NoaPpLat5FilingConstants.PARID_MIN_LENGTH, max = NoaPpLat5FilingConstants.PARID_MAX_LENGTH)
    @Column(name = "PARID")
    private String parid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TAXYR")
    private int taxyr;
    @Basic(optional = false)
    @NotNull
    @Size(min = NoaPpLat5FilingConstants.CATEGORY_MIN_LENGTH, max = NoaPpLat5FilingConstants.CATEGORY_MAX_LENGTH)
    @Column(name = "CATEGORY")
    private String category;
    @Basic(optional = false)
    @NotNull
    @Size(min = NoaPpLat5FilingConstants.PPTYPE_MIN_LENGTH, max = NoaPpLat5FilingConstants.PPTYPE_MAX_LENGTH)
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
    @Size(max = NoaPpLat5FilingConstants.COSIGNER_OWNER_NAME_MAX_LENGTH)
    @Column(name = "CONSIGNER_OWNER_NAME")
    private String consignerOwnerName;
    @Size(max = NoaPpLat5FilingConstants.COSIGNER_MAILING_ADDR_MAX_LENGTH)
    @Column(name = "CONSIGNER_MAILING_ADDR")
    private String consignerMailingAddr;
    @Column(name = "CONSIGNER_RENTAL_AMT")
    private Long consignerRentalAmt;
    @Size(max = NoaPpLat5FilingConstants.ITEM_DESCRIPTION_MAX_LENGTH)
    @Column(name = "ITEM_DESCRIPTION")
    private String itemDescription;
    @Size(max = NoaPpLat5FilingConstants.COSIGNER_TEL_NO_MAX_LENGTH)
    @Column(name = "CONSIGNER_TEL_NO")
    private String consignerTelNo;
    @JoinColumn(name = "NOA_PP_LAT_5_ID", referencedColumnName = "NOA_PP_LAT_5_ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private NoaPpLat5Entity noaPpLat5;
    @JoinColumn(name = "PROPERTY_ASSET_ID", referencedColumnName = "PROPERTY_ASSET_ID")
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private PropertyAssetEntity propertyAsset;

    public NoaPpLat5FilingEntity(Integer noaPpLat5FilingId)
    {
        this.noaPpLat5FilingId = noaPpLat5FilingId;
    }

    public NoaPpLat5FilingEntity(Integer noaPpLat5FilingId, String jur, String parid, int taxyr, String category, String pptype, int fileyr)
    {
        this.noaPpLat5FilingId = noaPpLat5FilingId;
        this.jur = jur;
        this.parid = parid;
        this.taxyr = taxyr;
        this.category = category;
        this.pptype = pptype;
        this.fileyr = fileyr;
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
        if (!(object instanceof NoaPpLat5FilingEntity)) {
            return false;
        }
        NoaPpLat5FilingEntity other = (NoaPpLat5FilingEntity) object;
        if ((this.noaPpLat5FilingId == null && other.noaPpLat5FilingId != null) || (this.noaPpLat5FilingId != null && !this.noaPpLat5FilingId.equals(other.noaPpLat5FilingId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "com.opao.pp_api.repositories.entities.NoaPpLat5FilingEntity[ noaPpLat5FilingId=" + noaPpLat5FilingId + " ]";
    }
    
}   
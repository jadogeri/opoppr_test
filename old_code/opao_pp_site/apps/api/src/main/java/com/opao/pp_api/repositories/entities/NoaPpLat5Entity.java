package com.opao.pp_api.repositories.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;

import com.opao.pp_api.common.constants.models.NoaPpLat5Constants;

import jakarta.validation.constraints.NotNull;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType; 
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;

@Entity
@Data // Generates Getters, Setters, toString, equals, and hashCode
@NoArgsConstructor // Generates a public no-argument constructor
@AllArgsConstructor // Generates a constructor with all fields
@Table(name = "noa_pp_lat5")
public class NoaPpLat5Entity implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "NOA_PP_LAT_5_ID")
    private Integer noaPpLat5Id;
    @Basic(optional = false)
    @NotNull
    @Size(min = NoaPpLat5Constants.JUR_MIN_LENGTH, max = NoaPpLat5Constants.JUR_MAX_LENGTH)
    @Column(name = "JUR")
    private String jur;
    @Basic(optional = false)
    @NotNull
    @Size(min = NoaPpLat5Constants.PARID_MIN_LENGTH, max = NoaPpLat5Constants.PARID_MAX_LENGTH)
    @Column(name = "PARID")
    private String parid;
    @Size(max = NoaPpLat5Constants.ALTID_MAX_LENGTH)
    @Column(name = "ALTID")
    private String altid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TAXYR")
    private int taxyr;
    @Size(max = NoaPpLat5Constants.OWNERNAME_MAX_LENGTH)
    @Column(name = "OWNERNAME")
    private String ownername;
    @JoinColumn(name = "BUSINESS_TYPE_ID", referencedColumnName = "BUSINESS_TYPE_ID")
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private BusinessTypeEntity businessType;
    @Size(max = NoaPpLat5Constants.ADDR1_MAX_LENGTH)
    @Column(name = "ADDR1")
    private String addr1;
    @Size(max = NoaPpLat5Constants.ADDR2_MAX_LENGTH)
    @Column(name = "ADDR2")
    private String addr2;
    @Size(max = NoaPpLat5Constants.CITYNAME_MAX_LENGTH)
    @Column(name = "CITYNAME")
    private String cityname;
    @Size(max = NoaPpLat5Constants.STATECODE_MAX_LENGTH)
    @Column(name = "STATECODE")
    private String statecode;
    @Size(max = NoaPpLat5Constants.ZIP1_MAX_LENGTH)
    @Column(name = "ZIP1")
    private String zip1;
    @Size(max = NoaPpLat5Constants.PIN_MAX_LENGTH)
    @Column(name = "PIN")
    private String pin;
    @Size(max = NoaPpLat5Constants.CONTACT_NAME_MAX_LENGTH)
    @Column(name = "CONTACT_NAME")
    private String contactName;
    @Size(max = NoaPpLat5Constants.CONTACT_PHONE_MAX_LENGTH)
    @Column(name = "CONTACT_PHONE")
    private String contactPhone;
    @Size(max = NoaPpLat5Constants.CONTACT_FAX_MAX_LENGTH)
    @Column(name = "CONTACT_FAX")
    private String contactFax;
    @Size(max = NoaPpLat5Constants.CONTACT_EMAIL_MAX_LENGTH)
    @Column(name = "CONTACT_EMAIL")
    private String contactEmail;
    @Column(name = "CONTACT_SEND_EMAILS")
    private Boolean contactSendEmails;
    @Size(max = NoaPpLat5Constants.PROPERTY_ADDRESS_MAX_LENGTH)
    @Column(name = "PROPERTY_ADDRESS")
    private String propertyAddress;
    @Size(max = NoaPpLat5Constants.TAXPAYER_NAME_MAX_LENGTH)
    @Column(name = "TAXPAYER_NAME")
    private String taxpayerName;
    @Column(name = "TAXPAYER_PREPARED_DATE")
    private LocalDateTime taxpayerPreparedDate;
    @Size(max = NoaPpLat5Constants.TAX_PREPARER_NAME_MAX_LENGTH)
    @Column(name = "TAX_PREPARER_NAME")
    private String taxPreparerName;
    @Size(max = NoaPpLat5Constants.TAX_PREPARER_PHONE_MAX_LENGTH)
    @Column(name = "TAX_PREPARER_PHONE")
    private String taxPreparerPhone;
    @Size(max = NoaPpLat5Constants.TAX_PREPARER_EMAIL_MAX_LENGTH)
    @Column(name = "TAX_PREPARER_EMAIL")
    private String taxPreparerEmail;
    @Column(name = "TAX_PREPARER_PREPARED_DATE")
    private LocalDateTime taxPreparerPreparedDate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "noaPpLat5", fetch = FetchType.LAZY)
    private Collection<NoaPpLat5InventoriesEntity> noaPpLat5InventoriesCollection;
    @JoinColumn(name = "FORM_ID", referencedColumnName = "FORM_ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private FormEntity form;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "noaPpLat5", fetch = FetchType.LAZY)
    private Collection<NoaPpLat5FilingEntity> noaPpLat5FilingCollection;

    public NoaPpLat5Entity(Integer noaPpLat5Id) {
        this.noaPpLat5Id = noaPpLat5Id;
    }

    public NoaPpLat5Entity(Integer noaPpLat5Id, String jur, String parid, int taxyr) {
        this.noaPpLat5Id = noaPpLat5Id;
        this.jur = jur;
        this.parid = parid;
        this.taxyr = taxyr;
    }
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (noaPpLat5Id != null ? noaPpLat5Id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NoaPpLat5Entity)) {
            return false;
        }
        NoaPpLat5Entity other = (NoaPpLat5Entity) object;
        if ((this.noaPpLat5Id == null && other.noaPpLat5Id != null)
                || (this.noaPpLat5Id != null && !this.noaPpLat5Id.equals(other.noaPpLat5Id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.opao.pp_api.repositories.entities.NoaPpLat5Entity[ noaPpLat5Id=" + noaPpLat5Id + " ]";
    }
    
}
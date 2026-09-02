package com.svlogic.opoppr.model;

import java.io.Serializable;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author david
 */
@Entity
@Table(name = "business_type")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "BusinessType.findAll", query = "SELECT b FROM BusinessType b"),
        @NamedQuery(name = "BusinessType.findByBusinessTypeId", query = "SELECT b FROM BusinessType b WHERE b.businessTypeId = :businessTypeId"),
        @NamedQuery(name = "BusinessType.findByBusinessCode", query = "SELECT b FROM BusinessType b WHERE b.businessCode = :businessCode"),
        @NamedQuery(name = "BusinessType.findByBusinessDescription", query = "SELECT b FROM BusinessType b WHERE b.businessDescription = :businessDescription") })
public class BusinessType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "BUSINESS_TYPE_ID")
    private Integer businessTypeId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "BUSINESS_CODE")
    private Integer businessCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "BUSINESS_DESCRIPTION")
    private String businessDescription;

    public BusinessType() {
    }

    public BusinessType(Integer businessTypeId) {
        this.businessTypeId = businessTypeId;
    }

    public BusinessType(Integer businessTypeId, Integer businessCode, String businessDescription) {
        this.businessTypeId = businessTypeId;
        this.businessCode = businessCode;
        this.businessDescription = businessDescription;
    }

    public Integer getBusinessTypeId() {
        return businessTypeId;
    }

    public void setBusinessTypeId(Integer businessTypeId) {
        this.businessTypeId = businessTypeId;
    }

    public Integer getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(Integer businessCode) {
        this.businessCode = businessCode;
    }

    public String getBusinessDescription() {
        return businessDescription;
    }

    public void setBusinessDescription(String businessDescription) {
        this.businessDescription = businessDescription;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (businessTypeId != null ? businessTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BusinessType)) {
            return false;
        }
        BusinessType other = (BusinessType) object;
        if ((this.businessTypeId == null && other.businessTypeId != null)
                || (this.businessTypeId != null && !this.businessTypeId.equals(other.businessTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.svlogic.opoppr.model.BusinessType[ businessTypeId=" + businessTypeId + " ]";
    }

}

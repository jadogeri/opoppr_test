package com.opao.pp_api.repositories.entities;

import java.io.Serializable;

import com.opao.pp_api.common.constants.models.BusinessTypeConstants;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Entity
@Data // Generates Getters, Setters, toString, equals, and hashCode
@NoArgsConstructor // Generates a public no-argument constructor
@AllArgsConstructor // Generates a constructor with all fields
@Table(name = "business_type")
public class BusinessTypeEntity implements Serializable {
    
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
    @Size(min = BusinessTypeConstants.BUSINESS_DESCRIPTION_MIN_LENGTH, 
          max = BusinessTypeConstants.BUSINESS_DESCRIPTION_MAX_LENGTH
    )
    @Column(name = "BUSINESS_DESCRIPTION")
    private String businessDescription;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (businessTypeId != null ? businessTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BusinessTypeEntity)) {
            return false;
        }
        BusinessTypeEntity other = (BusinessTypeEntity) object;
        if ((this.businessTypeId == null && other.businessTypeId != null)
                || (this.businessTypeId != null && !this.businessTypeId.equals(other.businessTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.opao.pp_api.repositories.entities.BusinessTypeEntity[ businessTypeId=" + businessTypeId + " ]";
    }
    
}

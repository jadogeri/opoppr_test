package com.opao.pp_api.repositories.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.opao.pp_api.common.constants.models.FormConstants;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;  

@Entity
@Data // Generates Getters, Setters, toString, equals, and hashCode
@NoArgsConstructor // Generates a public no-argument constructor
@AllArgsConstructor // Generates a constructor with all fields
@Table(name = "form")
public class FormEntity implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "FORM_ID")
    private Integer formId;
    @Basic(optional = false)
    @NotNull
    @Size(min = FormConstants.TITLE_MIN_LENGTH, max = FormConstants.TITLE_MAX_LENGTH)
    @Column(name = "TITLE")
    private String title;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FILING_YEAR")
    private int filingYear;
    @Column(name = "LAST_MODIFIED_DATE")
    private LocalDateTime lastModifiedDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = FormConstants.BILL_NUMBER_MIN_LENGTH, max = FormConstants.BILL_NUMBER_MAX_LENGTH)
    @Column(name = "BILL_NUMBER")
    private String billNumber;
    @Basic(optional = false)
    @NotNull
    @Size(min = FormConstants.PIN_MIN_LENGTH, max = FormConstants.PIN_MAX_LENGTH)
    @Column(name = "PIN")
    private String pin;
    @JoinColumn(name = "FORM_TYPE_ID", referencedColumnName = "FORM_TYPE_ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private FormTypeEntity formType;
    @JoinColumn(name = "STATUS_ID", referencedColumnName = "STATUS_ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private FormStatusEntity status;
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    @ManyToOne
    private UserEntity userId;
    @OneToMany(cascade = jakarta.persistence.CascadeType.ALL, mappedBy = "form", fetch = FetchType.LAZY)
    private List<NoaPpLat5Entity> noaPpLat5Collection;

    public FormEntity(Integer formId) {
        this.formId = formId;
    }

    public FormEntity(Integer formId, String title, int filingYear) {
        this.formId = formId;
        this.title = title;
        this.filingYear = filingYear;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (formId != null ? formId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FormEntity)) {
            return false;
        }
        FormEntity other = (FormEntity) object;
        if ((this.formId == null && other.formId != null)
                || (this.formId != null && !this.formId.equals(other.formId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.opao.pp_api.repositories.entities.FormEntity[ formId=" + formId + " ]";
    }   
    
}
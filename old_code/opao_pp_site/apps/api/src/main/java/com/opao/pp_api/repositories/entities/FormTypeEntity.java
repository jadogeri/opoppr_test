package com.opao.pp_api.repositories.entities;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

import com.opao.pp_api.common.constants.models.FormTypeConstants;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;

@Entity
@Data // Generates Getters, Setters, toString, equals, and hashCode
@NoArgsConstructor // Generates a public no-argument constructor
@AllArgsConstructor // Generates a constructor with all fields
@Table(name = "form_type")
public class FormTypeEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "FORM_TYPE_ID")
    private Integer formTypeId;
    @Basic(optional = false)
    @NotNull
    @Size(min = FormTypeConstants.FORM_NAME_MIN_LENGTH, max = FormTypeConstants.FORM_NAME_MAX_LENGTH)
    @Column(name = "FORM_NAME")
    private String formName;

    public FormTypeEntity(Integer formTypeId)
    {
        this.formTypeId = formTypeId;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (formTypeId != null ? formTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FormTypeEntity)) {
            return false;
        }
        FormTypeEntity other = (FormTypeEntity) object;
        if ((this.formTypeId == null && other.formTypeId != null) || (this.formTypeId != null && !this.formTypeId.equals(other.formTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "com.opao.pp_api.repositories.entities.FormTypeEntity[ formTypeId=" + formTypeId + " ]";
    }
}
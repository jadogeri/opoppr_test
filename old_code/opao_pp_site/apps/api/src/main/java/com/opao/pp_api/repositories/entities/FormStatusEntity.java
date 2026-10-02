package com.opao.pp_api.repositories.entities;

import java.io.Serializable;

import com.opao.pp_api.common.constants.models.FormStatusConstants;

import jakarta.validation.constraints.NotNull;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data // Generates Getters, Setters, toString, equals, and hashCode
@NoArgsConstructor // Generates a public no-argument constructor
@AllArgsConstructor // Generates a constructor with all fields
@Entity
@Table(name = "form_status")
public class FormStatusEntity implements Serializable {
    static public final FormStatusEntity NEW = new FormStatusEntity(1);
    static public final FormStatusEntity IN_PROGRESS = new FormStatusEntity(2);
    static public final FormStatusEntity SUBMITTED = new FormStatusEntity(3);
    static public final FormStatusEntity CLOSED = new FormStatusEntity(4);
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "STATUS_ID")
    private Integer statusId;
    @Basic(optional = false)
    @NotNull        
    @Size(min = FormStatusConstants.NAME_MIN_LENGTH, max = FormStatusConstants.NAME_MAX_LENGTH)
    @Column(name = "NAME")
    private String name;

    public FormStatusEntity(Integer statusId)
    {
        this.statusId = statusId;
    }

        @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (statusId != null ? statusId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FormStatusEntity)) {
            return false;
        }
        FormStatusEntity other = (FormStatusEntity) object;
        if ((this.statusId == null && other.statusId != null) || (this.statusId != null && !this.statusId.equals(other.statusId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "com.opao.pp_api.repositories.entities.FormStatusEntity[ statusId=" + statusId + " ]";
    }
}

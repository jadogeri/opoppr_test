/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.svlogic.opoppr.model;

import java.io.*;
import java.util.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

/**
 *
 * @author David
 */
@Entity
@Table(name = "form_type")
@NamedQueries({
    @NamedQuery(name = "FormType.findAll", query = "SELECT f FROM FormType f"),
    @NamedQuery(name = "FormType.findByFormTypeId", query = "SELECT f FROM FormType f WHERE f.formTypeId = :formTypeId"),
    @NamedQuery(name = "FormType.findByFormName", query = "SELECT f FROM FormType f WHERE f.formName = :formName")})
public class FormType implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "FORM_TYPE_ID")
    private Integer formTypeId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "FORM_NAME")
    private String formName;

    public FormType()
    {
    }

    public FormType(Integer formTypeId)
    {
        this.formTypeId = formTypeId;
    }

    public FormType(Integer formTypeId, String formName)
    {
        this.formTypeId = formTypeId;
        this.formName = formName;
    }

    public Integer getFormTypeId()
    {
        return formTypeId;
    }

    public void setFormTypeId(Integer formTypeId)
    {
        this.formTypeId = formTypeId;
    }

    public String getFormName()
    {
        return formName;
    }

    public void setFormName(String formName)
    {
        this.formName = formName;
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
        if (!(object instanceof FormType)) {
            return false;
        }
        FormType other = (FormType) object;
        if ((this.formTypeId == null && other.formTypeId != null) || (this.formTypeId != null && !this.formTypeId.equals(other.formTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "com.svlogic.opoppr.model.FormType[ formTypeId=" + formTypeId + " ]";
    }
    
}

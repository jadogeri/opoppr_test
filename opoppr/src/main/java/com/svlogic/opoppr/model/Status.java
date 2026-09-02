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
@Table(name = "status")
@NamedQueries({
    @NamedQuery(name = "Status.findAll", query = "SELECT s FROM Status s"),
    @NamedQuery(name = "Status.findByStatusId", query = "SELECT s FROM Status s WHERE s.statusId = :statusId"),
    @NamedQuery(name = "Status.findByName", query = "SELECT s FROM Status s WHERE s.name = :name")})
public class Status implements Serializable
{
    static public final Status NEW = new Status(1);
    static public final Status IN_PROGRESS = new Status(2);
    static public final Status SUBMITTED = new Status(3);
    static public final Status CLOSED = new Status(4);
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "STATUS_ID")
    private Integer statusId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "NAME")
    private String name;

    public Status()
    {
    }

    public Status(Integer statusId)
    {
        this.statusId = statusId;
    }

    public Status(Integer statusId, String name)
    {
        this.statusId = statusId;
        this.name = name;
    }

    public Integer getStatusId()
    {
        return statusId;
    }

    public void setStatusId(Integer statusId)
    {
        this.statusId = statusId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
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
        if (!(object instanceof Status)) {
            return false;
        }
        Status other = (Status) object;
        if ((this.statusId == null && other.statusId != null) || (this.statusId != null && !this.statusId.equals(other.statusId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "com.svlogic.opoppr.model.Status[ statusId=" + statusId + " ]";
    }
    
}

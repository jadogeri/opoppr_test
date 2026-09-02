/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.svlogic.opoppr.model;

import java.io.Serializable;
import java.util.Collection;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;

/**
 *
 * @author david
 */
@Entity
@Table(name = "user_change_type")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserChangeType.findAll", query = "SELECT u FROM UserChangeType u"),
    @NamedQuery(name = "UserChangeType.findByUserChangeTypeId", query = "SELECT u FROM UserChangeType u WHERE u.userChangeTypeId = :userChangeTypeId"),
    @NamedQuery(name = "UserChangeType.findByName", query = "SELECT u FROM UserChangeType u WHERE u.name = :name")})
public class UserChangeType implements Serializable {
    static public final UserChangeType CHANGE_PASSWORD = new UserChangeType(2);
    static public final UserChangeType ACTIVATE = new UserChangeType(1);
    static public final UserChangeType GET_USERNAME = new UserChangeType(3);

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "USER_CHANGE_TYPE_ID")
    private Integer userChangeTypeId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "NAME")
    private String name;

    public UserChangeType() {
    }

    public UserChangeType(Integer userChangeTypeId) {
        this.userChangeTypeId = userChangeTypeId;
    }

    public UserChangeType(Integer userChangeTypeId, String name) {
        this.userChangeTypeId = userChangeTypeId;
        this.name = name;
    }

    public Integer getUserChangeTypeId() {
        return userChangeTypeId;
    }

    public void setUserChangeTypeId(Integer userChangeTypeId) {
        this.userChangeTypeId = userChangeTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userChangeTypeId != null ? userChangeTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserChangeType)) {
            return false;
        }
        UserChangeType other = (UserChangeType) object;
        if ((this.userChangeTypeId == null && other.userChangeTypeId != null) || (this.userChangeTypeId != null && !this.userChangeTypeId.equals(other.userChangeTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.svlogic.opoppr.model.UserChangeType[ userChangeTypeId=" + userChangeTypeId + " ]";
    }
    
}

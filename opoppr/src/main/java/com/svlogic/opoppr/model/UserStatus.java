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
@Table(name = "user_status")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "UserStatus.findAll", query = "SELECT u FROM UserStatus u"),
        @NamedQuery(name = "UserStatus.findByUserStatusId", query = "SELECT u FROM UserStatus u WHERE u.userStatusId = :userStatusId"),
        @NamedQuery(name = "UserStatus.findByName", query = "SELECT u FROM UserStatus u WHERE u.name = :name") })
public class UserStatus implements Serializable {
    static final public UserStatus DISABLED = new UserStatus(1, "Disabled");
    static final public UserStatus ENABLED = new UserStatus(2, "Enabled");
    static final public UserStatus LOCKED = new UserStatus(3, "Locked");
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "USER_STATUS_ID")
    private Integer userStatusId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "NAME")
    private String name;

    public UserStatus() {
    }

    public UserStatus(Integer userStatusId) {
        this.userStatusId = userStatusId;
    }

    public UserStatus(Integer userStatusId, String name) {
        this.userStatusId = userStatusId;
        this.name = name;
    }

    public Integer getUserStatusId() {
        return userStatusId;
    }

    public void setUserStatusId(Integer userStatusId) {
        this.userStatusId = userStatusId;
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
        hash += (userStatusId != null ? userStatusId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserStatus)) {
            return false;
        }
        UserStatus other = (UserStatus) object;
        if ((this.userStatusId == null && other.userStatusId != null)
                || (this.userStatusId != null && !this.userStatusId.equals(other.userStatusId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.svlogic.opoppr.model.UserStatus[ userStatusId=" + userStatusId + " ]";
    }

}

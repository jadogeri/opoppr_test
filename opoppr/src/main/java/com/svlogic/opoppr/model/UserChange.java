/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.svlogic.opoppr.model;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author david
 */
@Entity
@Table(name = "user_change")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserChange.findAll", query = "SELECT u FROM UserChange u"),
    @NamedQuery(name = "UserChange.findByUserChangeId", query = "SELECT u FROM UserChange u WHERE u.userChangeId = :userChangeId"),
    @NamedQuery(name = "UserChange.findByVerificationCode", query = "SELECT u FROM UserChange u WHERE u.verificationCode = :verificationCode"),
    @NamedQuery(name = "UserChange.findByInitiatedTime", query = "SELECT u FROM UserChange u WHERE u.initiatedTime = :initiatedTime")})
public class UserChange implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "USER_CHANGE_ID")
    private Integer userChangeId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "VERIFICATION_CODE")
    private String verificationCode;
    @Basic(optional = false)
    @NotNull
    @Column(name = "INITIATED_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date initiatedTime = new Date();
    @JoinColumn(name = "USER_CHANGE_TYPE_ID", referencedColumnName = "USER_CHANGE_TYPE_ID")
    @ManyToOne(optional = false)
    private UserChangeType userChangeTypeId;
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    @ManyToOne(optional = false)
    private User userId;

    public UserChange() {
    }

    public UserChange(Integer userChangeId) {
        this.userChangeId = userChangeId;
    }

    public UserChange(Integer userChangeId, String verificationCode, Date initiatedTime) {
        this.userChangeId = userChangeId;
        this.verificationCode = verificationCode;
        this.initiatedTime = initiatedTime;
    }

    public Integer getUserChangeId() {
        return userChangeId;
    }

    public void setUserChangeId(Integer userChangeId) {
        this.userChangeId = userChangeId;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public Date getInitiatedTime() {
        return initiatedTime;
    }

    public void setInitiatedTime(Date initiatedTime) {
        this.initiatedTime = initiatedTime;
    }

    public UserChangeType getUserChangeTypeId() {
        return userChangeTypeId;
    }

    public void setUserChangeTypeId(UserChangeType userChangeTypeId) {
        this.userChangeTypeId = userChangeTypeId;
    }
    
    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userChangeId != null ? userChangeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserChange)) {
            return false;
        }
        UserChange other = (UserChange) object;
        if ((this.userChangeId == null && other.userChangeId != null) || (this.userChangeId != null && !this.userChangeId.equals(other.userChangeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.svlogic.opoppr.model.UserChange[ userChangeId=" + userChangeId + " ]";
    }
    
}

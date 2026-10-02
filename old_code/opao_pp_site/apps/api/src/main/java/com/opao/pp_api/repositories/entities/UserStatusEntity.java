package com.opao.pp_api.repositories.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;

import com.opao.pp_api.common.constants.models.UserStatusConstants;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Data // Generates Getters, Setters, toString, equals, and hashCode
@NoArgsConstructor // Generates a public no-argument constructor
@AllArgsConstructor // Generates a constructor with all fields
@Table(name = "user_status")
public class UserStatusEntity implements Serializable{
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "USER_STATUS_ID")
    private Integer userStatusId;
    @Basic(optional = false)
    @NotNull
    @Size(min = UserStatusConstants.STATUS_NAME_MIN_LENGTH, max = UserStatusConstants.STATUS_NAME_MAX_LENGTH)
    @Column(name = "NAME")
    private String name;

    public UserStatusEntity(Integer userStatusId) {
        this.userStatusId = userStatusId;
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
        if (!(object instanceof UserStatusEntity)) {
            return false;
        }
        UserStatusEntity other = (UserStatusEntity) object;
        if ((this.userStatusId == null && other.userStatusId != null)
                || (this.userStatusId != null && !this.userStatusId.equals(other.userStatusId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.opao.pp_api.repositories.entities.UserStatusEntity[ userStatusId=" + userStatusId + " ]";
    }

}

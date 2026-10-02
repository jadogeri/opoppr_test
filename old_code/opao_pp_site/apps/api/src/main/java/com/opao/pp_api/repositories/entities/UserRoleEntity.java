package com.opao.pp_api.repositories.entities;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;

import com.opao.pp_api.common.constants.models.UserRoleConstants;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
    
@Entity
@Data // Generates Getters, Setters, toString, equals, and hashCode
@NoArgsConstructor // Generates a public no-argument constructor
@AllArgsConstructor // Generates a constructor with all fields
@Table(name = "user_role")
public class UserRoleEntity implements Serializable{

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "USER_ROLE_ID")
    private Integer userRoleId;
    @Basic(optional = false)
    @NotNull
    @Size(min = UserRoleConstants.ROLE_NAME_MIN_LENGTH, max = UserRoleConstants.ROLE_NAME_MAX_LENGTH)
    @Column(name = "NAME")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = UserRoleConstants.ROLE_DESCRIPTION_MIN_LENGTH, max = UserRoleConstants.ROLE_DESCRIPTION_MAX_LENGTH)
    @Column(name = "DESCRIPTION")
    private String description;

    public UserRoleEntity(Integer userRoleId) {
        this.userRoleId = userRoleId;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userRoleId != null ? userRoleId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserRoleEntity)) {
            return false;
        }
        UserRoleEntity other = (UserRoleEntity) object;
        if ((this.userRoleId == null && other.userRoleId != null) || (this.userRoleId != null && !this.userRoleId.equals(other.userRoleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.opao.pp_api.repositories.entities.UserRoleEntity[ userRoleId=" + userRoleId + " ]";
    }
}

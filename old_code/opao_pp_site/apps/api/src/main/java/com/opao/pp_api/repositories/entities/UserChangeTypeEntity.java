package com.opao.pp_api.repositories.entities;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;

import com.opao.pp_api.common.constants.models.UserChangeTypeConstants;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Data // Generates Getters, Setters, toString, equals, and hashCode
@NoArgsConstructor // Generates a public no-argument constructor
@AllArgsConstructor // Generates a constructor with all fields
@Table(name = "user_change_type")
public class UserChangeTypeEntity implements Serializable{
    static public final UserChangeTypeEntity CHANGE_PASSWORD = new UserChangeTypeEntity(2);
    static public final UserChangeTypeEntity ACTIVATE = new UserChangeTypeEntity(1);
    static public final UserChangeTypeEntity GET_USERNAME = new UserChangeTypeEntity(3);

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "USER_CHANGE_TYPE_ID")
    private Integer userChangeTypeId;
    @Basic(optional = false)
    @NotNull
    @Size(min = UserChangeTypeConstants.USER_CHANGE_TYPE_NAME_MIN_LENGTH, max = UserChangeTypeConstants.USER_CHANGE_TYPE_NAME_MAX_LENGTH)
    @Column(name = "NAME")
    private String name;

    public UserChangeTypeEntity(Integer userChangeTypeId) {
        this.userChangeTypeId = userChangeTypeId;
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
        if (!(object instanceof UserChangeTypeEntity)) {
            return false;
        }
        UserChangeTypeEntity other = (UserChangeTypeEntity) object;
        if ((this.userChangeTypeId == null && other.userChangeTypeId != null) || (this.userChangeTypeId != null && !this.userChangeTypeId.equals(other.userChangeTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.opao.pp_api.repositories.entities.UserChangeTypeEntity[ userChangeTypeId=" + userChangeTypeId + " ]";
    }
    
}

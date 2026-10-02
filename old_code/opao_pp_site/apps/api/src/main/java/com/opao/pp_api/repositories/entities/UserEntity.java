package com.opao.pp_api.repositories.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;

import com.opao.pp_api.common.constants.models.UserConstants;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data // Generates Getters, Setters, toString, equals, and hashCode
@NoArgsConstructor // Generates a public no-argument constructor
@AllArgsConstructor // Generates a constructor with all fields
@Table(name = "user")
public class UserEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "USER_ID")
    private Integer userId;
    @Basic(optional = false)
    @NotNull
    @Size(min = UserConstants.USERNAME_MIN_LENGTH, max = UserConstants.USERNAME_MAX_LENGTH)
    @Column(name = "USERNAME")
    private String username;

    @Basic(optional = false)
    @NotNull
    @Size(min = UserConstants.PASSWORD_MIN_LENGTH, max = UserConstants.PASSWORD_MAX_LENGTH)
    @Column(name = "PASSWORD")
    private String password;

    @Basic(optional = false)
    @NotNull
    @Size(min = UserConstants.FULL_NAME_MIN_LENGTH, max = UserConstants.FULL_NAME_MAX_LENGTH)
    @Column(name = "FULL_NAME")
    private String fullName;

    @Basic(optional = false)
    @NotNull
    @Size(min = UserConstants.EMAIL_ADDRESS_MIN_LENGTH, max = UserConstants.EMAIL_ADDRESS_MAX_LENGTH)
    @Column(name = "EMAIL_ADDRESS")
    private String emailAddress;

    @Basic(optional = false)
    @NotNull
    @Size(min = UserConstants.PHONE_NUMBER_MIN_LENGTH, max = UserConstants.PHONE_NUMBER_MAX_LENGTH)
    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATION_TIME")
    private LocalDateTime creationTime = LocalDateTime.now();

    @Column(name = "LAST_LOGIN_TIME")
    private LocalDateTime lastLoginTime;

    @JoinColumn(name = "USER_STATUS_ID", referencedColumnName = "USER_STATUS_ID")
    @ManyToOne(optional = false)
    private UserStatusEntity userStatus;

    @JoinColumn(name = "USER_ROLE_ID", referencedColumnName = "USER_ROLE_ID")
    @ManyToOne(optional = false)
    private UserRoleEntity userRoleId;

    @OneToMany(mappedBy = "userId")
    private Collection<FormEntity> formCollection;

    @Column(name = "FAILED_LOGINS")
    @Basic(optional = true)
    private Integer failedLogins = 0;

    public UserEntity(Integer userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof UserEntity)) {
            return false;
        }
        UserEntity other = (UserEntity) object;
        if ((this.userId == null && other.userId != null)
                || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.opao.pp_api.repositories.entities.UserEntity[ userId=" + userId + " ]";
    }
  
}
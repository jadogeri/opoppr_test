package com.opao.pp_api.controllers.mapper;

import com.opao.pp_api.controllers.dto.request.UserRegistrationRequest;
import com.opao.pp_api.controllers.dto.request.UserUpdateRequest;
import com.opao.pp_api.controllers.dto.response.UserRegistrationResponse;
import com.opao.pp_api.controllers.dto.response.UserUpdateResponse;
import com.opao.pp_api.services.domains.User;

import org.springframework.stereotype.Component;

@Component
public class UserDtoMapper {

    public User toDomain(UserRegistrationRequest request) {
        if (request == null) return null;
        
        return User.builder()
                .username(request.username())
                .fullName(request.fullName())
                .email(request.emailAddress()) 
                .phoneNumber(request.phoneNumber())
                .clearTextPassword(request.password()) 
                .isActive(true) 
                .build();
    }

    public User toDomain(UserUpdateRequest request) {
        if (request == null) return null;
        
        return User.builder()
                .username(request.username())
                .fullName(request.fullName())                
                .email(request.email())
                .phoneNumber(request.phoneNumber())
                .clearTextPassword(request.clearTextPassword())
                .build();
    }

    public User toDomain(UserRegistrationResponse response) {
        if (response == null) return null;
        
        return User.builder()
                .id(response.id()) 
                .username(response.username())
                .fullName(response.fullName())
                .email(response.emailAddress()) 
                .phoneNumber(response.phoneNumber())
                .isActive(true)
                .build();
    }

    // 💡 UNIQUE NAME: Changed to toRegistrationResponse
    public UserRegistrationResponse toRegistrationResponse(User user) {
        if (user == null) return null;
        
        return new UserRegistrationResponse(
            user.getId(), 
            user.getUsername(),
            user.getFullName(),
            user.getEmail(), 
            user.getPhoneNumber()
        );
    }    

    // 💡 UNIQUE NAME: Changed to toUpdateResponse
    public UserUpdateResponse toUpdateResponse(User user) {
        if (user == null) return null;
        
        Integer roleId = (user.getUserRoleId() != null) ? user.getUserRoleId().getId() : null;
        Integer statusId = (user.getUserStatusId() != null) ? user.getUserStatusId().getId() : null;
        
        return new UserUpdateResponse(
            user.getId(), 
            user.getUsername(),
            user.getFullName(),
            user.getEmail(), 
            user.getPhoneNumber(),
            user.isActive(),
            roleId,
            statusId
        );
    }    
}

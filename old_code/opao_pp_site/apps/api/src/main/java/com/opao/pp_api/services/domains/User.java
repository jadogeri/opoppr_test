package com.opao.pp_api.services.domains;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer id;
    private String username;
    private String fullName;      
    private String email;         
    private String phoneNumber;    
    private String clearTextPassword;
    private String hashedPassword;
    private boolean isActive;
    
    // FIXED: Added missing relation destination slots for MapStruct flattening
    private UserRole userRoleId;   
    private UserStatus userStatusId; 
}

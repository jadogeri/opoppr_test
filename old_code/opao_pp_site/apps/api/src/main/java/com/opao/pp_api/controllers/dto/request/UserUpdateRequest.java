package com.opao.pp_api.controllers.dto.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserUpdateRequest(
    // Added username slot to catch your request payload
    @Size(max = 50, message = "Username must not exceed 50 characters")
    String username,

    @Size(max = 100, message = "Full name must not exceed 100 characters")
    String fullName,
    
    @Pattern(
        regexp = "^$|^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", 
        message = "Please provide a valid email address format"
    )
    @Size(max = 255, message = "Email address must not exceed 255 characters")
    String email,
    
    @Size(max = 20, message = "Phone number must not exceed 20 characters")
    String phoneNumber,
    
    @Pattern(
        regexp = "^$|.{8,}", 
        message = "If updating password, it must be at least 8 characters long"
    )
    String clearTextPassword 
) {}

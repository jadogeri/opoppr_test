package com.opao.pp_api.controllers.dto.response;

// FIXED: Corrected spelling to match UserController imports exactly
public record UserRegistrationResponse (
    Integer id,
    String username,
    String fullName,
    String emailAddress,
    String phoneNumber
) {}

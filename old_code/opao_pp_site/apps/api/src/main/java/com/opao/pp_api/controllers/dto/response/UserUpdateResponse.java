package com.opao.pp_api.controllers.dto.response;

public record UserUpdateResponse(
    Integer id,
    String username,
    String fullName,
    String email,
    String phoneNumber,
    boolean isActive,
    Integer userRoleId,
    Integer userStatusId
) {}

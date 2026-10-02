package com.opao.pp_api.controllers.dto.request;

import com.opao.pp_api.common.constants.models.UserConstants;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRegistrationRequest(
    @NotBlank(message = "Username is required")
    @Size(min = 4, max = UserConstants.USERNAME_MAX_LENGTH, message = "Username must be between 4 and " + UserConstants.USERNAME_MAX_LENGTH + " characters")
    String username,

    @NotBlank(message = "Full name is required")
    @Size(min = UserConstants.FULL_NAME_MIN_LENGTH, max = UserConstants.FULL_NAME_MAX_LENGTH, message = "Full name must be between " + UserConstants.FULL_NAME_MIN_LENGTH + " and " + UserConstants.FULL_NAME_MAX_LENGTH + " characters")
    String fullName,

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    String emailAddress,

    @Size(min = UserConstants.PHONE_NUMBER_MIN_LENGTH, max = UserConstants.PHONE_NUMBER_MAX_LENGTH, message = "Phone number must be between " + UserConstants.PHONE_NUMBER_MIN_LENGTH + " and " + UserConstants.PHONE_NUMBER_MAX_LENGTH + " characters")
    String phoneNumber,

    @NotBlank(message = "Password is required")
    @Size(min = UserConstants.PASSWORD_MIN_LENGTH, max = UserConstants.PASSWORD_MAX_LENGTH, message = "Password must be between " + UserConstants.PASSWORD_MIN_LENGTH + " and " + UserConstants.PASSWORD_MAX_LENGTH + " characters")
    String password
) {}

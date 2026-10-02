package com.opao.pp_api.common.constants.models;


public final class UserConstants {
    private UserConstants() {} // Prevent instantiation

    // Centralized source of truth for lengths
    public static final int USERNAME_MIN_LENGTH = 1;
    public static final int USERNAME_MAX_LENGTH = 75;
    
    public static final int PASSWORD_MIN_LENGTH = 1;
    public static final int PASSWORD_MAX_LENGTH = 255;

    public static final int FULL_NAME_MIN_LENGTH = 1;
    public static final int FULL_NAME_MAX_LENGTH = 50;

    public static final int EMAIL_ADDRESS_MIN_LENGTH = 1;
    public static final int EMAIL_ADDRESS_MAX_LENGTH = 75;

    public static final int PHONE_NUMBER_MIN_LENGTH = 1;
    public static final int PHONE_NUMBER_MAX_LENGTH = 10;


}

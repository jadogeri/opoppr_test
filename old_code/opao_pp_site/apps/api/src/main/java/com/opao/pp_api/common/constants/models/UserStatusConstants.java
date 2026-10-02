package com.opao.pp_api.common.constants.models;


public final class UserStatusConstants {
    private UserStatusConstants() {} // Prevent instantiation

    // Centralized source of truth for lengths
    public static final int DISABLED_ID = 1; // Example value, change as needed
    public static final String DISABLED_TEXT = "Disabled"; // Example value, change as needed
    public static final int ENABLED_ID = 2; // Example value, change as needed
    public static final String ENABLED_TEXT = "Enabled"; // Example value, change as needed 
    public static final int LOCKED_ID = 3; // Example value, change as needed
    public static final String LOCKED_TEXT = "Locked"; // Example value, change as needed 
    public static final int STATUS_NAME_MIN_LENGTH = 1;
    public static final int STATUS_NAME_MAX_LENGTH = 45;
}

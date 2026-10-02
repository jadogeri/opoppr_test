package com.opao.pp_api.common.constants.collections;

import com.opao.pp_api.services.domains.UserStatus;
import java.util.List;
public final class UserStatuses {

    private UserStatuses() {}

    static final public UserStatus DISABLED = new UserStatus(1, "Disabled");
    static final public UserStatus ENABLED = new UserStatus(2, "Enabled");
    static final public UserStatus LOCKED = new UserStatus(3, "Locked");

    public static final List<UserStatus> ALL_STATUSES = List.of(DISABLED, ENABLED, LOCKED);

}

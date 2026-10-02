package com.opao.pp_api.common.constants.collections;

import com.opao.pp_api.services.domains.UserRole;
import java.util.List;

public final class UserRoles {

    private UserRoles() {}

    public static final UserRole ADMINISTRATOR = new UserRole(1);
    public static final UserRole SUPERUSER = new UserRole(2);
    public static final UserRole TAX_PREPARER = new UserRole(3);

    public static final List<UserRole> ALL_ROLES = List.of(ADMINISTRATOR, SUPERUSER, TAX_PREPARER);

}

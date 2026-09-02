package com.svlogic.opoppr.admin;


import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

import com.svlogic.opoppr.model.UserRole;

import java.util.HashMap;
import java.util.Map;

import com.svlogic.opoppr.CreateUserAccount;

@Named("adminCreateUserAccount")
@RequestScoped
public class AdminCreateUserAccount extends CreateUserAccount {

    private String roleNameKey;

    private final Map<String, UserRole> roles = new HashMap<String, UserRole>(){{
        put("Administrator", UserRole.ADMINISTRATOR);
        put("Superuser", UserRole.SUPERUSER);
        put("Tax Preparer", UserRole.TAX_PREPARER);
    }};
    
    public AdminCreateUserAccount() {
        super();
    }

    @Override
    public String createAccount() {
        String ret = "success";
        boolean accountCreated = this.getUserSession().adminCreateUserAccount(this.getEmailAddress(), this.getPassword(), this.getFullName(), this.getPhoneNumber(), this.getEmailAddress(), this.getRole() );
        if (!accountCreated) {
            ret = "failure";
            this.setCreateUserAccountFailed(true);
        }
        return ret;
    }

    public void setRoleNameKey(String roleNameKey){
        this.roleNameKey = roleNameKey;

    }

    public String getRoleNameKey(){
        return this.roleNameKey ;
    }

    public UserRole getRole() {
        if(roles.containsKey(this.roleNameKey)){
            return roles.get(this.roleNameKey);
        }
        else{
            throw new IllegalArgumentException("role '"+ roleNameKey + "' is not a valid option");
        }

    }

}

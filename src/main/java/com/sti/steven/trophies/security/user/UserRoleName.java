package com.sti.steven.trophies.security.user;

public enum UserRoleName {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");


    private final String roleName;

    UserRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}

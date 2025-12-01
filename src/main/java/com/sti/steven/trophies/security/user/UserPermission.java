package com.sti.steven.trophies.security.user;

public enum UserPermission {
    GET_TROPHIES("GET_TROPHIES"),
    POST_TROPHIES("POST_TROPHIES"),
    PUT_TROPHIES("PUT_TROPHIES"),
    DELETE_TROPHIES("DELETE_TROPHIES"),
    MANAGE_TROPHIES("MANAGE_TROPHIES");

    private final String permission;

    UserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}

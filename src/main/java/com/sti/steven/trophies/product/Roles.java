package com.sti.steven.trophies.product;

public enum Roles {
    ADMIN("ADMIN:CAN_POST"),
    USER("USER:CAN_READ");

    private final String permissions;

    Roles(String permissions) {
        this.permissions = permissions;
    }
}

package com.projetoFinalWeb.projetoWeb.model;

public enum UserRole {

    ADMIN("ADMIN_ROLE"),
    USER("USER_ROLE");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}

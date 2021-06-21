package com.book.bookapi.model.user;

import java.util.Set;

public enum UserRole {
    USER(Set.of(Permission.USER)),
    ADMIN(Set.of(Permission.USER, Permission.ADMIN));

    UserRole(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    private final Set<Permission> permissions;
}

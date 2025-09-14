package com.sinaukoding.library_management_system.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Role {
    ROLE_ADMIN("Admin"),
    ROLE_MEMBER("Member");

    private final String label;
}

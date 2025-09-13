package com.sinaukoding.library_management_system.model.enums;

import lombok.Getter;

@Getter
public enum Status {
    AKTIF("Aktif"),
    TIDAK_AKTIF("Tidak aktif");

    private final String label;

    Status(String label) {
        this.label = label;
    }
}

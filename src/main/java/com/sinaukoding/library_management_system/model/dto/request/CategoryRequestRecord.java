package com.sinaukoding.library_management_system.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryRequestRecord(
        @NotBlank(message = "Nama kategori tidak boleh kosong")
        @Size(max = 100, message = "Nama kategori maksimal 100 karakter")
        String nama,
        
        @Size(max = 255, message = "Deskripsi maksimal 255 karakter")
        String deskripsi
) {
}

package com.sinaukoding.library_management_system.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequestRecord(
        @NotBlank(message = "Username tidak boleh kosong")
        @Size(max = 50, message = "Maksimal 50 karakter")
        String username,
        
        @NotBlank(message = "Password tidak boleh kosong")
        @Size(min = 6, message = "Minimal 6 karakter")
        String password,
        
        @NotBlank(message = "Nama lengkap tidak boleh kosong")
        @Size(max = 100, message = "Maksimal 100 karakter")
        String namaLengkap,
        
        @NotBlank(message = "Email tidak boleh kosong")
        @Email(message = "Format email tidak valid")
        @Size(max = 100, message = "Maksimal 100 karakter")
        String email
) {
}

package com.sinaukoding.library_management_system.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Year;

public record BukuRequestRecord(
        @NotBlank(message = "Judul tidak boleh kosong")
        @Size(max = 255, message = "Judul maksimal 255 karakter")
        String judul,

        @NotBlank(message = "Penulis tidak boleh kosong")
        @Size(max = 100, message = "Penulis maksimal 100 karakter")
        String penulis,

        @NotBlank(message = "ISBN tidak boleh kosong")
        @Size(min = 10, max = 13, message = "ISBN harus 10 atau 13 karakter")
        String isbn,

        @NotNull(message = "Tahun terbit tidak boleh kosong")
        Year tahunTerbit,

        @NotBlank(message = "Genre tidak boleh kosong")
        String genre
) {}
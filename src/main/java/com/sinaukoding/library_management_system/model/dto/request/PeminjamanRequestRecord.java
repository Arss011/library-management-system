package com.sinaukoding.library_management_system.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record PeminjamanRequestRecord(
        @NotBlank(message = "Buku ID tidak boleh kosong")
        String bukuId,
        
        @NotNull(message = "Tanggal peminjaman tidak boleh kosong")
        LocalDateTime tanggalPeminjaman,
        
        @NotNull(message = "Tanggal jatuh tempo tidak boleh kosong")
        LocalDateTime tanggalJatuhTempo
) {
}

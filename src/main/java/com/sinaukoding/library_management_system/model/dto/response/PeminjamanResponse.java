package com.sinaukoding.library_management_system.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PeminjamanResponse {
    private String id;
    private UserResponse user;
    private BukuResponse buku;
    private LocalDateTime tanggalPeminjaman;
    private LocalDateTime tanggalJatuhTempo;
    private LocalDateTime tanggalPengembalian;
    private String statusPeminjaman;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private String createdBy;
    private String updateBy;
}

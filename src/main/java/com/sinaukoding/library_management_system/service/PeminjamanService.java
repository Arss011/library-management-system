package com.sinaukoding.library_management_system.service;

import com.sinaukoding.library_management_system.entity.transaction.Peminjaman;
import com.sinaukoding.library_management_system.model.dto.request.PeminjamanRequestRecord;

import java.util.List;
import java.util.Optional;

public interface PeminjamanService {
    List<Peminjaman> findAll();
    Optional<Peminjaman> findById(String id);
    List<Peminjaman> findByUserId(String userId);
    List<Peminjaman> findByBukuId(String bukuId);
    Peminjaman pinjamBuku(String userId, PeminjamanRequestRecord request);
    Peminjaman kembalikanBuku(String peminjamanId, String userId);
    Peminjaman update(String id, PeminjamanRequestRecord request);
    void deleteById(String id);
}

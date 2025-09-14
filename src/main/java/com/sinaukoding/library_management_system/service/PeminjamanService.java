package com.sinaukoding.library_management_system.service;

import com.sinaukoding.library_management_system.model.dto.request.PeminjamanRequestRecord;
import com.sinaukoding.library_management_system.model.dto.response.PeminjamanResponse;

import java.util.List;
import java.util.Optional;

public interface PeminjamanService {
    List<PeminjamanResponse> findAll();
    Optional<PeminjamanResponse> findById(String id);
    List<PeminjamanResponse> findByUserId(String userId);
    List<PeminjamanResponse> findByBukuId(String bukuId);
    PeminjamanResponse pinjamBuku(String userId, PeminjamanRequestRecord request);
    PeminjamanResponse kembalikanBuku(String peminjamanId, String userId);
    PeminjamanResponse update(String id, PeminjamanRequestRecord request);
    void deleteById(String id);
}

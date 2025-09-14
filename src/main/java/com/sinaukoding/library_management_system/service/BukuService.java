package com.sinaukoding.library_management_system.service;

import com.sinaukoding.library_management_system.entity.master.Buku;
import com.sinaukoding.library_management_system.model.dto.request.BukuRequestRecord;
import com.sinaukoding.library_management_system.model.dto.response.BukuResponse;

import java.util.List;
import java.util.Optional;

public interface BukuService {
    List<BukuResponse> findAll();
    Optional<BukuResponse> findById(String id);
    BukuResponse save(BukuRequestRecord request);
    BukuResponse update(String id, BukuRequestRecord request);
    void deleteById(String id);
    BukuResponse reduceStock(String bukuId, Integer quantity);
    BukuResponse increaseStock(String bukuId, Integer quantity);
}

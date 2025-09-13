package com.sinaukoding.library_management_system.service;

import com.sinaukoding.library_management_system.entity.master.Buku;
import com.sinaukoding.library_management_system.model.dto.request.BukuRequestRecord;

import java.util.List;
import java.util.Optional;

public interface BukuService {
    List<Buku> findAll();
    Optional<Buku> findById(String id);
    Buku save(BukuRequestRecord request);
    Buku update(String id, BukuRequestRecord request);
    void deleteById(String id);
}

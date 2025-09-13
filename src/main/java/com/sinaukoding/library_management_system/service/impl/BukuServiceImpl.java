package com.sinaukoding.library_management_system.service.impl;

import com.sinaukoding.library_management_system.entity.master.Buku;
import com.sinaukoding.library_management_system.model.dto.request.BukuRequestRecord;
import com.sinaukoding.library_management_system.model.enums.Status;
import com.sinaukoding.library_management_system.repository.BukuRepository;
import com.sinaukoding.library_management_system.service.BukuService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BukuServiceImpl implements BukuService {

    private final BukuRepository bukuRepository;

    public BukuServiceImpl(BukuRepository bukuRepository) {
        this.bukuRepository = bukuRepository;
    }

    @Override
    public List<Buku> findAll() {
        return bukuRepository.findAll();
    }

    @Override
    public Optional<Buku> findById(String id) {
        return bukuRepository.findById(id);
    }

    @Override
    public Buku save(BukuRequestRecord request) {
        Buku buku = Buku.builder()
                .judul(request.judul())
                .penulis(request.penulis())
                .isbn(request.isbn())
                .tahunTerbit(request.tahunTerbit())
                .genre(request.genre())
                .isTersedia(true)
                .status(Status.AKTIF)
                .build();
        return bukuRepository.save(buku);
    }

    @Override
    public Buku update(String id, BukuRequestRecord request) {
        Buku existingBuku = bukuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Buku tidak ditemukan"));

        existingBuku.setJudul(request.judul());
        existingBuku.setPenulis(request.penulis());
        existingBuku.setIsbn(request.isbn());
        existingBuku.setTahunTerbit(request.tahunTerbit());
        existingBuku.setGenre(request.genre());

        return bukuRepository.save(existingBuku);
    }

    @Override
    public void deleteById(String id) {
        bukuRepository.deleteById(id);
    }
}

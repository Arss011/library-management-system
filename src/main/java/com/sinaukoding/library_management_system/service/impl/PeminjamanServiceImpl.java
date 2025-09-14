package com.sinaukoding.library_management_system.service.impl;

import com.sinaukoding.library_management_system.entity.master.Buku;
import com.sinaukoding.library_management_system.entity.managementuser.User;
import com.sinaukoding.library_management_system.entity.transaction.Peminjaman;
import com.sinaukoding.library_management_system.model.dto.request.PeminjamanRequestRecord;
import com.sinaukoding.library_management_system.repository.BukuRepository;
import com.sinaukoding.library_management_system.repository.PeminjamanRepository;
import com.sinaukoding.library_management_system.repository.managementuser.UserRepository;
import com.sinaukoding.library_management_system.service.BukuService;
import com.sinaukoding.library_management_system.service.PeminjamanService;
import com.sinaukoding.library_management_system.service.app.ValidatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PeminjamanServiceImpl implements PeminjamanService {

    private final PeminjamanRepository peminjamanRepository;
    private final BukuRepository bukuRepository;
    private final UserRepository userRepository;
    private final ValidatorService validatorService;
    private final BukuService bukuService;

    @Override
    public List<Peminjaman> findAll() {
        return peminjamanRepository.findAll();
    }

    @Override
    public Optional<Peminjaman> findById(String id) {
        return peminjamanRepository.findById(id);
    }

    @Override
    public List<Peminjaman> findByUserId(String userId) {
        return peminjamanRepository.findByUserId(userId);
    }

    @Override
    public List<Peminjaman> findByBukuId(String bukuId) {
        return peminjamanRepository.findByBukuId(bukuId);
    }

    @Override
    @Transactional
    public Peminjaman pinjamBuku(String userId, PeminjamanRequestRecord request) {
        validatorService.validator(request);
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User tidak ditemukan"));
        
        Buku buku = bukuRepository.findById(request.bukuId())
                .orElseThrow(() -> new RuntimeException("Buku tidak ditemukan"));
        
        if (!buku.getIsTersedia() || buku.getStock() <= 0) {
            throw new RuntimeException("Buku sedang tidak tersedia atau stock habis");
        }
        
        Peminjaman peminjaman = Peminjaman.builder()
                .user(user)
                .buku(buku)
                .tanggalPeminjaman(request.tanggalPeminjaman())
                .tanggalJatuhTempo(request.tanggalJatuhTempo())
                .statusPeminjaman("DIPINJAM")
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .createdBy("system") 
                .updateBy("system")
                .build();
        
        bukuService.reduceStock(request.bukuId(), 1);
        
        return peminjamanRepository.save(peminjaman);
    }

    @Override
    @Transactional
    public Peminjaman kembalikanBuku(String peminjamanId, String userId) {
        Peminjaman peminjaman = peminjamanRepository.findById(peminjamanId)
                .orElseThrow(() -> new RuntimeException("Peminjaman tidak ditemukan"));
        
        if (!peminjaman.getUser().getId().equals(userId)) {
            throw new RuntimeException("Anda tidak memiliki akses untuk mengembalikan buku ini");
        }
        
        if ("DIKEMBALIKAN".equals(peminjaman.getStatusPeminjaman())) {
            throw new RuntimeException("Buku sudah dikembalikan");
        }
        
        peminjaman.setStatusPeminjaman("DIKEMBALIKAN");
        peminjaman.setTanggalPengembalian(LocalDateTime.now());
        peminjaman.setModifiedDate(LocalDateTime.now());
        peminjaman.setUpdateBy("system");
        
        bukuService.increaseStock(peminjaman.getBuku().getId(), 1);
        
        return peminjamanRepository.save(peminjaman);
    }

    @Override
    public Peminjaman update(String id, PeminjamanRequestRecord request) {
        validatorService.validator(request);
        
        Peminjaman existingPeminjaman = peminjamanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Peminjaman tidak ditemukan"));
        
        Buku buku = bukuRepository.findById(request.bukuId())
                .orElseThrow(() -> new RuntimeException("Buku tidak ditemukan"));
        
        existingPeminjaman.setBuku(buku);
        existingPeminjaman.setTanggalPeminjaman(request.tanggalPeminjaman());
        existingPeminjaman.setTanggalJatuhTempo(request.tanggalJatuhTempo());
        existingPeminjaman.setModifiedDate(LocalDateTime.now());
        existingPeminjaman.setUpdateBy("system");
        
        return peminjamanRepository.save(existingPeminjaman);
    }

    @Override
    public void deleteById(String id) {
        peminjamanRepository.deleteById(id);
    }
}

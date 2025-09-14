package com.sinaukoding.library_management_system.service.impl;

import com.sinaukoding.library_management_system.entity.master.Buku;
import com.sinaukoding.library_management_system.entity.managementuser.User;
import com.sinaukoding.library_management_system.entity.transaction.Peminjaman;
import com.sinaukoding.library_management_system.model.dto.request.PeminjamanRequestRecord;
import com.sinaukoding.library_management_system.model.dto.response.BukuResponse;
import com.sinaukoding.library_management_system.model.dto.response.CategoryResponse;
import com.sinaukoding.library_management_system.model.dto.response.PeminjamanResponse;
import com.sinaukoding.library_management_system.model.dto.response.UserResponse;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PeminjamanServiceImpl implements PeminjamanService {

    private final PeminjamanRepository peminjamanRepository;
    private final BukuRepository bukuRepository;
    private final UserRepository userRepository;
    private final ValidatorService validatorService;
    private final BukuService bukuService;

    // Mapper methods
    private PeminjamanResponse mapToPeminjamanResponse(Peminjaman peminjaman) {
        return PeminjamanResponse.builder()
                .id(peminjaman.getId())
                .user(mapToUserResponse(peminjaman.getUser()))
                .buku(mapToBukuResponse(peminjaman.getBuku()))
                .tanggalPeminjaman(peminjaman.getTanggalPeminjaman())
                .tanggalJatuhTempo(peminjaman.getTanggalJatuhTempo())
                .tanggalPengembalian(peminjaman.getTanggalPengembalian())
                .statusPeminjaman(peminjaman.getStatusPeminjaman())
                .createdDate(peminjaman.getCreatedDate())
                .modifiedDate(peminjaman.getModifiedDate())
                .createdBy(peminjaman.getCreatedBy())
                .updateBy(peminjaman.getUpdateBy())
                .build();
    }

    private UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .namaLengkap(user.getNamaLengkap())
                .email(user.getEmail())
                .role(user.getRole())
                .expiredTokenAt(user.getExpiredTokenAt())
                .build();
    }

    private BukuResponse mapToBukuResponse(Buku buku) {
        return BukuResponse.builder()
                .id(buku.getId())
                .judul(buku.getJudul())
                .penulis(buku.getPenulis())
                .isbn(buku.getIsbn())
                .tahunTerbit(buku.getTahunTerbit())
                .category(mapToCategoryResponse(buku.getCategory()))
                .isTersedia(buku.getIsTersedia())
                .stock(buku.getStock())
                .build();
    }

    private CategoryResponse mapToCategoryResponse(com.sinaukoding.library_management_system.entity.master.Category category) {
        if (category == null) {
            return null;
        }
        return CategoryResponse.builder()
                .id(category.getId())
                .nama(category.getNama())
                .deskripsi(category.getDeskripsi())
                .build();
    }

    @Override
    public List<PeminjamanResponse> findAll() {
        return peminjamanRepository.findAll().stream()
                .map(this::mapToPeminjamanResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PeminjamanResponse> findById(String id) {
        return peminjamanRepository.findById(id)
                .map(this::mapToPeminjamanResponse);
    }

    @Override
    public List<PeminjamanResponse> findByUserId(String userId) {
        return peminjamanRepository.findByUserId(userId).stream()
                .map(this::mapToPeminjamanResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<PeminjamanResponse> findByBukuId(String bukuId) {
        return peminjamanRepository.findByBukuId(bukuId).stream()
                .map(this::mapToPeminjamanResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PeminjamanResponse pinjamBuku(String userId, PeminjamanRequestRecord request) {
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
        
        Peminjaman savedPeminjaman = peminjamanRepository.save(peminjaman);
        return mapToPeminjamanResponse(savedPeminjaman);
    }

    @Override
    @Transactional
    public PeminjamanResponse kembalikanBuku(String peminjamanId, String userId) {
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
        
        Peminjaman savedPeminjaman = peminjamanRepository.save(peminjaman);
        return mapToPeminjamanResponse(savedPeminjaman);
    }

    @Override
    public PeminjamanResponse update(String id, PeminjamanRequestRecord request) {
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
        
        Peminjaman savedPeminjaman = peminjamanRepository.save(existingPeminjaman);
        return mapToPeminjamanResponse(savedPeminjaman);
    }

    @Override
    public void deleteById(String id) {
        peminjamanRepository.deleteById(id);
    }
}

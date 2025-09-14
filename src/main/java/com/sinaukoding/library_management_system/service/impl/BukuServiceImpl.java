package com.sinaukoding.library_management_system.service.impl;

import com.sinaukoding.library_management_system.entity.master.Buku;
import com.sinaukoding.library_management_system.entity.master.Category;
import com.sinaukoding.library_management_system.model.dto.request.BukuRequestRecord;
import com.sinaukoding.library_management_system.model.dto.response.BukuResponse;
import com.sinaukoding.library_management_system.model.dto.response.CategoryResponse;
import com.sinaukoding.library_management_system.model.enums.Status;
import com.sinaukoding.library_management_system.repository.BukuRepository;
import com.sinaukoding.library_management_system.repository.CategoryRepository;
import com.sinaukoding.library_management_system.service.BukuService;
import com.sinaukoding.library_management_system.util.AuditUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BukuServiceImpl implements BukuService {

    private final BukuRepository bukuRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public List<BukuResponse> findAll() {
        return bukuRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<BukuResponse> findById(String id) {
        return bukuRepository.findById(id)
                .map(this::convertToDto);
    }

    @Override
    public BukuResponse save(BukuRequestRecord request) {
        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new RuntimeException("Kategori tidak ditemukan"));

        Buku buku = Buku.builder()
                .judul(request.judul())
                .penulis(request.penulis())
                .isbn(request.isbn())
                .tahunTerbit(request.tahunTerbit())
                .category(category)
                .stock(request.stock())
                .isTersedia(request.stock() > 0)
                .status(Status.AKTIF)
                .build();

        Buku savedBuku = bukuRepository.save(buku);
        return convertToDto(savedBuku);
    }

    @Override
    public BukuResponse update(String id, BukuRequestRecord request) {
        Buku existingBuku = bukuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Buku tidak ditemukan"));

        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new RuntimeException("Kategori tidak ditemukan"));

        existingBuku.setJudul(request.judul());
        existingBuku.setPenulis(request.penulis());
        existingBuku.setIsbn(request.isbn());
        existingBuku.setTahunTerbit(request.tahunTerbit());
        existingBuku.setCategory(category);
        existingBuku.setStock(request.stock());
        existingBuku.setIsTersedia(request.stock() > 0);

        Buku updatedBuku = bukuRepository.save(existingBuku);
        return convertToDto(updatedBuku);
    }

    @Override
    public void deleteById(String id) {
        bukuRepository.deleteById(id);
    }

    @Override
    @Transactional
    public BukuResponse reduceStock(String bukuId, Integer quantity) {
        Buku buku = bukuRepository.findById(bukuId)
                .orElseThrow(() -> new RuntimeException("Buku tidak ditemukan"));

        if (buku.getStock() < quantity) {
            throw new RuntimeException("Stock buku tidak mencukupi. Stock tersedia: " + buku.getStock());
        }

        buku.setStock(buku.getStock() - quantity);
        buku.setIsTersedia(buku.getStock() > 0);

        Buku updatedBuku = bukuRepository.save(buku);
        return convertToDto(updatedBuku);
    }

    @Override
    @Transactional
    public BukuResponse increaseStock(String bukuId, Integer quantity) {
        Buku buku = bukuRepository.findById(bukuId)
                .orElseThrow(() -> new RuntimeException("Buku tidak ditemukan"));

        buku.setStock(buku.getStock() + quantity);
        buku.setIsTersedia(true);

        Buku updatedBuku = bukuRepository.save(buku);
        return convertToDto(updatedBuku);
    }

    private BukuResponse convertToDto(Buku buku) {
        BukuResponse dto = new BukuResponse();
        dto.setId(buku.getId());
        dto.setJudul(buku.getJudul());
        dto.setPenulis(buku.getPenulis());
        dto.setIsbn(buku.getIsbn());
        dto.setTahunTerbit(buku.getTahunTerbit());
        dto.setIsTersedia(buku.getIsTersedia());
        dto.setStock(buku.getStock());

        if (buku.getCategory() != null) {
            CategoryResponse categoryDto = new CategoryResponse();
            categoryDto.setId(buku.getCategory().getId());
            categoryDto.setNama(buku.getCategory().getNama());
            categoryDto.setDeskripsi(buku.getCategory().getDeskripsi());
            dto.setCategory(categoryDto);
        }

        return dto;
    }
}
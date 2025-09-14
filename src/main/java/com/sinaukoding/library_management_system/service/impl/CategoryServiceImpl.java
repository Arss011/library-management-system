package com.sinaukoding.library_management_system.service.impl;

import com.sinaukoding.library_management_system.entity.master.Category;
import com.sinaukoding.library_management_system.model.dto.request.CategoryRequestRecord;
import com.sinaukoding.library_management_system.model.dto.response.CategoryResponse;
import com.sinaukoding.library_management_system.repository.CategoryRepository;
import com.sinaukoding.library_management_system.service.CategoryService;
import com.sinaukoding.library_management_system.service.app.ValidatorService;
import com.sinaukoding.library_management_system.util.AuditUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ValidatorService validatorService;

    @Override
    public List<CategoryResponse> findAll() {
        return categoryRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CategoryResponse> findById(String id) {
        return categoryRepository.findById(id)
                .map(this::convertToDto);
    }

    @Override
    public CategoryResponse save(CategoryRequestRecord request) {
        validatorService.validator(request);

        if (categoryRepository.existsByNama(request.nama())) {
            throw new RuntimeException("Nama kategori sudah digunakan");
        }

        Category category = Category.builder()
                .nama(request.nama())
                .deskripsi(request.deskripsi())
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .createdBy(AuditUtil.getCurrentUser())
                .updateBy(AuditUtil.getCurrentUser())
                .build();

        Category savedCategory = categoryRepository.save(category);
        return convertToDto(savedCategory);
    }

    @Override
    public CategoryResponse update(String id, CategoryRequestRecord request) {
        validatorService.validator(request);

        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kategori tidak ditemukan"));

        if (categoryRepository.existsByNamaAndIdNot(request.nama(), id)) {
            throw new RuntimeException("Nama kategori sudah digunakan");
        }

        existingCategory.setNama(request.nama());
        existingCategory.setDeskripsi(request.deskripsi());
        existingCategory.setModifiedDate(LocalDateTime.now());
        existingCategory.setUpdateBy(AuditUtil.getCurrentUser());

        Category updatedCategory = categoryRepository.save(existingCategory);
        return convertToDto(updatedCategory);
    }

    @Override
    public void deleteById(String id) {
        categoryRepository.deleteById(id);
    }

    private CategoryResponse convertToDto(Category category) {
        CategoryResponse dto = new CategoryResponse();
        dto.setId(category.getId());
        dto.setNama(category.getNama());
        dto.setDeskripsi(category.getDeskripsi());
        return dto;
    }
}
package com.sinaukoding.library_management_system.service;

import com.sinaukoding.library_management_system.entity.master.Category;
import com.sinaukoding.library_management_system.model.dto.request.CategoryRequestRecord;
import com.sinaukoding.library_management_system.model.dto.response.CategoryResponse;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<CategoryResponse> findAll();
    Optional<CategoryResponse> findById(String id);
    CategoryResponse save(CategoryRequestRecord request);
    CategoryResponse update(String id, CategoryRequestRecord request);
    void deleteById(String id);
}

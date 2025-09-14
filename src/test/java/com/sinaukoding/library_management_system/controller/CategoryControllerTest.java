package com.sinaukoding.library_management_system.controller;

import com.sinaukoding.library_management_system.controller.master.CategoryController;
import com.sinaukoding.library_management_system.model.dto.response.CategoryResponse;
import com.sinaukoding.library_management_system.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    @Test
    void testGetAllCategory_Success() {
        var category1 = CategoryResponse.builder()
                .id("1")
                .nama("Programming")
                .deskripsi("Books about programming languages and software development")
                .build();

        var category2 = CategoryResponse.builder()
                .id("2")
                .nama("Fiction")
                .deskripsi("Fictional books and novels")
                .build();

        var expectedCategories = Arrays.asList(category1, category2);
        when(categoryService.findAll()).thenReturn(expectedCategories);

        var result = categoryController.getAllCategory();

        verify(categoryService, times(1)).findAll();
    }
}

package com.sinaukoding.library_management_system.controller;

import com.sinaukoding.library_management_system.controller.master.BukuController;
import com.sinaukoding.library_management_system.model.dto.response.BukuResponse;
import com.sinaukoding.library_management_system.service.BukuService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Year;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BukuControllerTest {

    @Mock
    private BukuService bukuService;

    @InjectMocks
    private BukuController bukuController;

    @Test
    void testGetAllBuku_Success() {
        var buku1 = BukuResponse.builder()
                .id("1")
                .judul("Java Programming")
                .penulis("John Doe")
                .tahunTerbit(Year.of(2023))
                .isbn("978-1234567890")
                .stock(10)
                .isTersedia(true)
                .build();

        var buku2 = BukuResponse.builder()
                .id("2")
                .judul("Spring Boot Guide")
                .penulis("Jane Smith")
                .tahunTerbit(Year.of(2023))
                .isbn("978-0987654321")
                .stock(5)
                .isTersedia(true)
                .build();

        var expectedBooks = Arrays.asList(buku1, buku2);
        when(bukuService.findAll()).thenReturn(expectedBooks);

        var result = bukuController.getAllBuku();

        verify(bukuService, times(1)).findAll();
    }
}

package com.sinaukoding.library_management_system.controller;

import com.sinaukoding.library_management_system.controller.PeminjamanController;
import com.sinaukoding.library_management_system.model.dto.response.BukuResponse;
import com.sinaukoding.library_management_system.model.dto.response.PeminjamanResponse;
import com.sinaukoding.library_management_system.model.dto.response.UserResponse;
import com.sinaukoding.library_management_system.model.enums.Role;
import com.sinaukoding.library_management_system.service.PeminjamanService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PeminjamanControllerTest {

    @Mock
    private PeminjamanService peminjamanService;

    @InjectMocks
    private PeminjamanController peminjamanController;

    @Test
    void testGetPeminjamanById_Success() {
        var peminjamanId = "1";
        var userResponse = UserResponse.builder()
                .id("user123")
                .username("testuser")
                .namaLengkap("Test User")
                .email("test@example.com")
                .role(Role.ROLE_MEMBER)
                .build();

        var bukuResponse = BukuResponse.builder()
                .id("buku456")
                .judul("Test Book")
                .penulis("Test Author")
                .isbn("978-1234567890")
                .build();

        var peminjamanResponse = PeminjamanResponse.builder()
                .id(peminjamanId)
                .user(userResponse)
                .buku(bukuResponse)
                .tanggalPeminjaman(LocalDateTime.now())
                .tanggalJatuhTempo(LocalDateTime.now().plusDays(7))
                .statusPeminjaman("ACTIVE")
                .build();

        when(peminjamanService.findById(peminjamanId)).thenReturn(Optional.of(peminjamanResponse));

        var result = peminjamanController.getPeminjamanById(peminjamanId);

        verify(peminjamanService, times(1)).findById(peminjamanId);
    }
}

package com.sinaukoding.library_management_system.controller;

import com.sinaukoding.library_management_system.entity.transaction.Peminjaman;
import com.sinaukoding.library_management_system.model.dto.request.PeminjamanRequestRecord;
import com.sinaukoding.library_management_system.security.UserLoggedInConfig;
import com.sinaukoding.library_management_system.service.PeminjamanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/peminjaman")
@RequiredArgsConstructor
public class PeminjamanController {

    private final PeminjamanService peminjamanService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Peminjaman>> getAllPeminjaman() {
        List<Peminjaman> listPeminjaman = peminjamanService.findAll();
        return ResponseEntity.ok(listPeminjaman);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Peminjaman> getPeminjamanById(@PathVariable String id) {
        return peminjamanService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user")
    public ResponseEntity<List<Peminjaman>> getPeminjamanByUser(@AuthenticationPrincipal UserLoggedInConfig userLoggedInConfig) {
        String userId = userLoggedInConfig.getUser().getId();
        List<Peminjaman> listPeminjaman = peminjamanService.findByUserId(userId);
        return ResponseEntity.ok(listPeminjaman);
    }

    @GetMapping("/buku/{bukuId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Peminjaman>> getPeminjamanByBuku(@PathVariable String bukuId) {
        List<Peminjaman> listPeminjaman = peminjamanService.findByBukuId(bukuId);
        return ResponseEntity.ok(listPeminjaman);
    }

    @PostMapping("/pinjam")
    public ResponseEntity<Peminjaman> pinjamBuku(
            @AuthenticationPrincipal UserLoggedInConfig userLoggedInConfig,
            @Valid @RequestBody PeminjamanRequestRecord request) {
        String userId = userLoggedInConfig.getUser().getId();
        Peminjaman peminjaman = peminjamanService.pinjamBuku(userId, request);
        return new ResponseEntity<>(peminjaman, HttpStatus.CREATED);
    }

    @PostMapping("/{id}/kembalikan")
    public ResponseEntity<Peminjaman> kembalikanBuku(
            @PathVariable String id,
            @AuthenticationPrincipal UserLoggedInConfig userLoggedInConfig) {
        String userId = userLoggedInConfig.getUser().getId();
        Peminjaman peminjaman = peminjamanService.kembalikanBuku(id, userId);
        return ResponseEntity.ok(peminjaman);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Peminjaman> updatePeminjaman(
            @PathVariable String id,
            @Valid @RequestBody PeminjamanRequestRecord request) {
        Peminjaman updatedPeminjaman = peminjamanService.update(id, request);
        return ResponseEntity.ok(updatedPeminjaman);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletePeminjaman(@PathVariable String id) {
        peminjamanService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

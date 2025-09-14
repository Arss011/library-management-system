package com.sinaukoding.library_management_system.controller;

import com.sinaukoding.library_management_system.entity.transaction.Peminjaman;
import com.sinaukoding.library_management_system.model.dto.request.PeminjamanRequestRecord;
import com.sinaukoding.library_management_system.model.dto.response.BaseResponse;
import com.sinaukoding.library_management_system.model.dto.response.PeminjamanResponse;
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
    public ResponseEntity<BaseResponse<List<PeminjamanResponse>>> getAllPeminjaman() {
        List<PeminjamanResponse> listPeminjaman = peminjamanService.findAll();
        return ResponseEntity.ok(BaseResponse.ok("Data peminjaman berhasil diambil", listPeminjaman));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<PeminjamanResponse>> getPeminjamanById(@PathVariable String id) {
        return peminjamanService.findById(id)
                .map(peminjaman -> ResponseEntity.ok(BaseResponse.ok("Data peminjaman berhasil diambil", peminjaman)))
                .orElse(ResponseEntity.ok(BaseResponse.badRequest("Data peminjaman tidak ditemukan")));
    }

    @GetMapping("/user")
    public ResponseEntity<BaseResponse<List<PeminjamanResponse>>> getPeminjamanByUser(@AuthenticationPrincipal UserLoggedInConfig userLoggedInConfig) {
        String userId = userLoggedInConfig.getUser().getId();
        List<PeminjamanResponse> listPeminjaman = peminjamanService.findByUserId(userId);
        return ResponseEntity.ok(BaseResponse.ok("Data peminjaman user berhasil diambil", listPeminjaman));
    }

    @GetMapping("/buku/{bukuId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponse<List<PeminjamanResponse>>> getPeminjamanByBuku(@PathVariable String bukuId) {
        List<PeminjamanResponse> listPeminjaman = peminjamanService.findByBukuId(bukuId);
        return ResponseEntity.ok(BaseResponse.ok("Data peminjaman buku berhasil diambil", listPeminjaman));
    }

    @PostMapping("/pinjam")
    public ResponseEntity<BaseResponse<PeminjamanResponse>> pinjamBuku(
            @AuthenticationPrincipal UserLoggedInConfig userLoggedInConfig,
            @Valid @RequestBody PeminjamanRequestRecord request) {
        String userId = userLoggedInConfig.getUser().getId();
        PeminjamanResponse peminjaman = peminjamanService.pinjamBuku(userId, request);
        return new ResponseEntity<>(BaseResponse.ok("Buku berhasil dipinjam", peminjaman), HttpStatus.CREATED);
    }

    @PostMapping("/{id}/kembalikan")
    public ResponseEntity<BaseResponse<PeminjamanResponse>> kembalikanBuku(
            @PathVariable String id,
            @AuthenticationPrincipal UserLoggedInConfig userLoggedInConfig) {
        String userId = userLoggedInConfig.getUser().getId();
        PeminjamanResponse peminjaman = peminjamanService.kembalikanBuku(id, userId);
        return ResponseEntity.ok(BaseResponse.ok("Buku berhasil dikembalikan", peminjaman));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponse<PeminjamanResponse>> updatePeminjaman(
            @PathVariable String id,
            @Valid @RequestBody PeminjamanRequestRecord request) {
        PeminjamanResponse updatedPeminjaman = peminjamanService.update(id, request);
        return ResponseEntity.ok(BaseResponse.ok("Data peminjaman berhasil diupdate", updatedPeminjaman));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponse<Void>> deletePeminjaman(@PathVariable String id) {
        peminjamanService.deleteById(id);
        return ResponseEntity.ok(BaseResponse.ok("Data peminjaman berhasil dihapus", null));
    }
}

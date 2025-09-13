package com.sinaukoding.library_management_system.controller.master;

import com.sinaukoding.library_management_system.entity.master.Buku;
import com.sinaukoding.library_management_system.model.dto.request.BukuRequestRecord;
import com.sinaukoding.library_management_system.service.BukuService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/buku")
public class BukuController {

    private final BukuService bukuService;

    public BukuController(BukuService bukuService) {
        this.bukuService = bukuService;
    }

    @GetMapping
    public ResponseEntity<List<Buku>> getAllBuku() {
        List<Buku> listBuku = bukuService.findAll();
        return ResponseEntity.ok(listBuku);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Buku> getBukuById(@PathVariable String id) {
        return bukuService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Buku> createBuku(@Valid @RequestBody BukuRequestRecord request) {
        Buku savedBuku = bukuService.save(request);
        return new ResponseEntity<>(savedBuku, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Buku> updateBuku(@PathVariable String id, @Valid @RequestBody BukuRequestRecord request) {
        Buku updatedBuku = bukuService.update(id, request);
        return ResponseEntity.ok(updatedBuku);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteBuku(@PathVariable String id) {
        bukuService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

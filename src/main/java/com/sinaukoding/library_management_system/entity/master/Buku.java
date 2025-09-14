package com.sinaukoding.library_management_system.entity.master;

import com.sinaukoding.library_management_system.entity.BaseEntity;
import com.sinaukoding.library_management_system.model.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.Year;

    @EqualsAndHashCode(callSuper = true)
    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    @Entity
    @Table(name = "m_buku", indexes = {
            @Index(name = "idx_buku_created_date", columnList = "createdDate"),
            @Index(name = "idx_buku_modified_date", columnList = "modifiedDate"),
            @Index(name = "idx_buku_status", columnList = "status"),
            @Index(name = "idx_buku_judul", columnList = "judul"),
            @Index(name = "idx_buku_isbn", columnList = "isbn")
    })
    public class Buku extends BaseEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        private String id;

        @Size(max = 255, message = "Maksimal 255 karakter")
        @Column(nullable = false)
        private String judul;

        @Size(max = 100, message = "Maksimal 100 karakter")
        @Column(nullable = false)
        private String penulis;

        @Size(min = 10, max = 13, message = "ISBN harus 10 atau 13 karakter")
        @Column(nullable = false, unique = true)
        private String isbn;

        @Column(name = "tahun_terbit", nullable = false)
        private Year tahunTerbit;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "category_id", nullable = false)
        private Category category;

        @Column(nullable = false)
        private Boolean isTersedia;

        @Min(value = 0, message = "Stock tidak boleh negatif")
        @Column(nullable = false)
        private Integer stock;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private Status status;
    }
package com.sinaukoding.library_management_system.entity.transaction;

import com.sinaukoding.library_management_system.entity.BaseEntity;
import com.sinaukoding.library_management_system.entity.master.Buku;
import com.sinaukoding.library_management_system.entity.managementuser.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_peminjaman", indexes = {
        @Index(name = "idx_peminjaman_created_date", columnList = "createdDate"),
        @Index(name = "idx_peminjaman_modified_date", columnList = "modifiedDate"),
        @Index(name = "idx_peminjaman_user_id", columnList = "user_id"),
        @Index(name = "idx_peminjaman_buku_id", columnList = "buku_id")
})
public class Peminjaman extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buku_id", nullable = false)
    private Buku buku;

    @Column(nullable = false)
    private LocalDateTime tanggalPeminjaman;

    @Column(nullable = false)
    private LocalDateTime tanggalJatuhTempo;

    private LocalDateTime tanggalPengembalian;

    @Column(nullable = false)
    private String statusPeminjaman;
}
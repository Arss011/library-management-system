package com.sinaukoding.library_management_system.repository;

import com.sinaukoding.library_management_system.entity.transaction.Peminjaman;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PeminjamanRepository extends JpaRepository<Peminjaman, String> {

    List<Peminjaman> findByUserId(String userId);
    List<Peminjaman> findByBukuId(String bukuId);
    List<Peminjaman> findByStatusPeminjaman(String status);
    
    @Query("SELECT p FROM Peminjaman p WHERE p.user.id = :userId AND p.statusPeminjaman = :status")
    List<Peminjaman> findByUserIdAndStatus(@Param("userId") String userId, @Param("status") String status);
    
    @Query("SELECT p FROM Peminjaman p WHERE p.buku.id = :bukuId AND p.statusPeminjaman = 'DIPINJAM'")
    List<Peminjaman> findActivePeminjamanByBukuId(@Param("bukuId") String bukuId);
}

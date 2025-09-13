package com.sinaukoding.library_management_system.repository;

import com.sinaukoding.library_management_system.entity.master.Buku;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BukuRepository extends JpaRepository<Buku, String> {

    Optional<Buku> findByIsbn(String isbn);

}

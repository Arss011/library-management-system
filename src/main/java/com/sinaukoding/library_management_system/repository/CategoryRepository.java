package com.sinaukoding.library_management_system.repository;

import com.sinaukoding.library_management_system.entity.master.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {

    Optional<Category> findByNama(String nama);
    Boolean existsByNama(String nama);
    Boolean existsByNamaAndIdNot(String nama, String id);
}

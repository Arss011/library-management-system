package com.sinaukoding.library_management_system.entity.master;

import com.sinaukoding.library_management_system.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "m_category", indexes = {
        @Index(name = "idx_category_created_date", columnList = "createdDate"),
        @Index(name = "idx_category_modified_date", columnList = "modifiedDate"),
        @Index(name = "idx_category_nama", columnList = "nama")
})
public class Category extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotBlank(message = "Nama kategori tidak boleh kosong")
    @Size(max = 100, message = "Nama kategori maksimal 100 karakter")
    @Column(nullable = false, unique = true)
    private String nama;

    @Size(max = 255, message = "Deskripsi maksimal 255 karakter")
    private String deskripsi;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Buku> bukuList;
}

package com.sinaukoding.library_management_system.entity.master;

import com.sinaukoding.library_management_system.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "m_user", indexes = {
        @Index(name = "idx_user_created_date", columnList = "createdDate"),
        @Index(name = "idx_user_modified_date", columnList = "modifiedDate"),
        @Index(name = "idx_user_username", columnList = "username")
})
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Size(max = 50, message = "Maksimal 50 karakter")
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Size(max = 100, message = "Maksimal 100 karakter")
    @Column(nullable = false)
    private String namaLengkap;

    @Size(max = 100, message = "Maksimal 100 karakter")
    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String role;
}
package com.sinaukoding.library_management_system.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

public class Buku {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private Integer id;
    private String title;
    private String author;
    private Integer isbn;
    private LocalDateTime publicationYear;
    private String genre;
    private boolean isAvailable;
}

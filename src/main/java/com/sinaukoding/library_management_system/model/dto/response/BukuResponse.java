package com.sinaukoding.library_management_system.model.dto.response;

import lombok.Data;

import java.time.Year;

@Data
public class BukuResponse {

    private String id;
    private String judul;
    private String penulis;
    private String isbn;
    private Year tahunTerbit;
    private CategoryResponse Category;
    private Boolean isTersedia;
    private Integer stock;

}
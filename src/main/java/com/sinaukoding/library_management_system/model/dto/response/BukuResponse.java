package com.sinaukoding.library_management_system.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Year;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BukuResponse {

    private String id;
    private String judul;
    private String penulis;
    private String isbn;
    private Year tahunTerbit;
    private CategoryResponse category;
    private Boolean isTersedia;
    private Integer stock;

}
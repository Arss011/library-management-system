package com.sinaukoding.library_management_system.model.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record StockRequestRecord(
        @NotNull(message = "Quantity tidak boleh kosong")
        @Min(value = 1, message = "Quantity harus lebih dari 0")
        Integer quantity
) {}

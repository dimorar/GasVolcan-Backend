package com.gasvolcan.gasvolcan_backend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BoletaItemRequest(
    @NotBlank String id,
    @NotNull @Min(1) Integer cantidad
) {}

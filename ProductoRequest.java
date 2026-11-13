package com.gasvolcan.gasvolcan_backend.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record ProductoRequest(
    String id,
    @NotBlank String name,
    String descripcion,
    @NotNull @DecimalMin("0.01") BigDecimal price,
    @NotNull @Min(0) Integer stock,
    @Min(0) Integer stockCritico,
    String img,
    @NotNull Long categoriaId
) {}

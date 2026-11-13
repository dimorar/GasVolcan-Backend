package com.gasvolcan.gasvolcan_backend.dto;

import java.math.BigDecimal;

public record ProductoResponse(
    String id, String name, String descripcion, BigDecimal price,
    Integer stock, Integer stockCritico, String img,
    Long categoriaId, String categoria
) {}

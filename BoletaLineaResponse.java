package com.gasvolcan.gasvolcan_backend.dto;

import java.math.BigDecimal;

public record BoletaLineaResponse(
    String id,
    String nombre,
    Integer cantidad,
    BigDecimal precio,
    BigDecimal subtotal
) {}

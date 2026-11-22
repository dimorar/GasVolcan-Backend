package com.gasvolcan.gasvolcan_backend.dto;

import java.math.BigDecimal;

public record BoletaLineaResponse(
    Long productoId,
    String producto,
    Integer cantidad,
    BigDecimal precioUnitario,
    BigDecimal subtotal
) {}
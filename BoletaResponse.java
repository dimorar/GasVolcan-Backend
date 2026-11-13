package com.gasvolcan.gasvolcan_backend.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record BoletaResponse(
    String id,
    LocalDateTime fecha,
    BigDecimal total,
    Long usuarioId,
    String cliente,
    String comuna,
    String direccion,
    String estado,
    List<BoletaLineaResponse> items
) {}

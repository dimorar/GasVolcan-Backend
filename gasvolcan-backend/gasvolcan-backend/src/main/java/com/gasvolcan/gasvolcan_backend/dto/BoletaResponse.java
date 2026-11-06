package gasvolcan.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record BoletaResponse(
    Long id,
    LocalDateTime fecha,
    BigDecimal total,
    Long usuarioId,
    List<BoletaLineaResponse> detalles
) {}
package gasvolcan.dto;

import java.math.BigDecimal;

public record ProductoResponse(
    Long id, String nombre, String descripcion, BigDecimal precio,
    Integer stock, String imagenUrl, Long categoriaId, String categoria
) {}
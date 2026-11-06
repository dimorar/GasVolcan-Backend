package gasvolcan.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record ProductoRequest(
    @NotBlank String nombre,
    String descripcion,
    @NotNull @DecimalMin("0.01") BigDecimal precio,
    @NotNull @Min(0) Integer stock,
    String imagenUrl,
    @NotNull Long categoriaId
) {}
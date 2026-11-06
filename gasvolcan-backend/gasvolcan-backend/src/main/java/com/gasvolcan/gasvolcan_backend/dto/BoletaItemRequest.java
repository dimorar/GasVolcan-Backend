package gasvolcan.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record BoletaItemRequest(
    @NotNull Long productoId,
    @NotNull @Min(1) Integer cantidad
) {}
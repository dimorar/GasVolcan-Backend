package com.gasvolcan.gasvolcan_backend.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.List;

public record BoletaRequest(
    @NotBlank @Size(min = 5, max = 100) String direccion,
    @NotEmpty List<@Valid BoletaItemRequest> items
) {}

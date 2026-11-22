package com.gasvolcan.gasvolcan_backend.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record BoletaRequest(
    @NotEmpty List<@Valid BoletaItemRequest> items
) {}
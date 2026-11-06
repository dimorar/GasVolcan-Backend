package gasvolcan.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoriaRequest(@NotBlank String nombre) {}
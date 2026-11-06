package gasvolcan.dto;

import jakarta.validation.constraints.*;

public record RegisterRequest(
    @NotBlank String nombre,
    @Email @NotBlank String email,
    @NotBlank @Size(min = 8) String password
) {}
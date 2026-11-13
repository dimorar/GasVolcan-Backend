package com.gasvolcan.gasvolcan_backend.dto;

import jakarta.validation.constraints.*;

public record UsuarioRequest(
    @NotBlank @Pattern(regexp = "^\\d{7,8}-[0-9Kk]$", message = "RUN inválido (ej: 12345678-5)") String rut,
    @NotBlank String nombre,
    @NotBlank @Pattern(
        regexp = "(?i)^[^\\s@]+@(duoc\\.cl|profesor\\.duoc\\.cl|gmail\\.com)$",
        message = "El correo debe ser @duoc.cl, @profesor.duoc.cl o @gmail.com") String email,
    @Size(min = 6) String password,
    @NotBlank String region,
    @NotBlank String comuna
) {}

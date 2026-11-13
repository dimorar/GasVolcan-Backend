package com.gasvolcan.gasvolcan_backend.dto;

public record AuthResponse(
    String token, String tokenType, Long id,
    String nombre, String email, String rol
) {}

package gasvolcan.dto;

public record AuthResponse(
    String token, String tokenType, Long userId,
    String nombre, String email, String rol
) {}
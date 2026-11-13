package com.gasvolcan.gasvolcan_backend.dto;

import com.gasvolcan.gasvolcan_backend.model.Usuario;

public record UsuarioResponse(
    Long id, String rut, String nombre, String email,
    String rol, String region, String comuna
) {
    public static UsuarioResponse de(Usuario u) {
        return new UsuarioResponse(
            u.getId(), u.getRut(), u.getNombre(), u.getEmail(),
            u.getRol().etiqueta(), u.getRegion(), u.getComuna()
        );
    }
}

package com.gasvolcan.gasvolcan_backend.service;

import com.gasvolcan.gasvolcan_backend.dto.*;
import com.gasvolcan.gasvolcan_backend.model.Usuario;
import com.gasvolcan.gasvolcan_backend.repository.UsuarioRepository;
import com.gasvolcan.gasvolcan_backend.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarios;
    private final UsuarioService usuarioService;
    private final PasswordEncoder encoder;
    private final JwtService jwt;

    public AuthService(
        UsuarioRepository usuarios,
        UsuarioService usuarioService,
        PasswordEncoder encoder,
        JwtService jwt
    ) {
        this.usuarios = usuarios;
        this.usuarioService = usuarioService;
        this.encoder = encoder;
        this.jwt = jwt;
    }

    public AuthResponse registrar(RegisterRequest r) {
        Usuario u = usuarioService.registrarCliente(
            r.rut(), r.nombre(), r.email(), r.password(), r.region(), r.comuna()
        );
        return respuesta(u);
    }

    public AuthResponse login(LoginRequest r) {
        Usuario u = usuarios.findByEmail(r.email().trim().toLowerCase())
            .orElseThrow(() ->
                new IllegalArgumentException("Credenciales incorrectas"));

        if (!u.isActivo()
            || !encoder.matches(r.password(), u.getPassword())) {
            throw new IllegalArgumentException(
                "Credenciales incorrectas"
            );
        }

        return respuesta(u);
    }

    private AuthResponse respuesta(Usuario u) {
        return new AuthResponse(
            jwt.generar(u.getEmail()),
            "Bearer",
            u.getId(),
            u.getNombre(),
            u.getEmail(),
            u.getRol().etiqueta()
        );
    }
}

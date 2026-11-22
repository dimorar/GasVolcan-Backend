package com.gasvolcan.gasvolcan_backend.controller;

import com.gasvolcan.gasvolcan_backend.dto.*;
import com.gasvolcan.gasvolcan_backend.model.Usuario;
import com.gasvolcan.gasvolcan_backend.repository.UsuarioRepository;
import com.gasvolcan.gasvolcan_backend.service.BoletaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/boletas")
public class BoletaController {

    private final BoletaService service;
    private final UsuarioRepository usuarios;

    public BoletaController(
        BoletaService service,
        UsuarioRepository usuarios
    ) {
        this.service = service;
        this.usuarios = usuarios;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BoletaResponse crear(
        @Valid @RequestBody BoletaRequest request,
        Authentication authentication
    ) {
        Usuario usuario = usuarios.findByEmail(authentication.getName())
            .orElseThrow(() ->
                new java.util.NoSuchElementException("Usuario no encontrado"));

        return service.crear(usuario.getId(), request);
    }
}
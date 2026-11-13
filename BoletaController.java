package com.gasvolcan.gasvolcan_backend.controller;

import com.gasvolcan.gasvolcan_backend.dto.*;
import com.gasvolcan.gasvolcan_backend.model.Usuario;
import com.gasvolcan.gasvolcan_backend.repository.UsuarioRepository;
import com.gasvolcan.gasvolcan_backend.service.BoletaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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
        return service.crear(usuarioActual(authentication).getId(), request);
    }

    @GetMapping
    public List<BoletaResponse> listar() {
        return service.listar();
    }

    @GetMapping("/mias")
    public List<BoletaResponse> listarMias(Authentication authentication) {
        return service.listarPorUsuario(usuarioActual(authentication).getId());
    }

    @PutMapping("/{id}/estado")
    public BoletaResponse cambiarEstado(
        @PathVariable String id,
        @Valid @RequestBody EstadoRequest request
    ) {
        return service.cambiarEstado(id, request.estado());
    }

    private Usuario usuarioActual(Authentication authentication) {
        return usuarios.findByEmail(authentication.getName())
            .orElseThrow(() ->
                new java.util.NoSuchElementException("Usuario no encontrado"));
    }
}

package com.gasvolcan.gasvolcan_backend.controller;

import com.gasvolcan.gasvolcan_backend.dto.UsuarioRequest;
import com.gasvolcan.gasvolcan_backend.dto.UsuarioResponse;
import com.gasvolcan.gasvolcan_backend.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @GetMapping
    public List<UsuarioResponse> listar() {
        return service.listar().stream().map(UsuarioResponse::de).toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponse crear(@Valid @RequestBody UsuarioRequest request) {
        if (request.password() == null || request.password().isBlank()) {
            throw new IllegalArgumentException("La contraseña es obligatoria");
        }
        return UsuarioResponse.de(service.registrarCliente(
            request.rut(), request.nombre(), request.email(),
            request.password(), request.region(), request.comuna()
        ));
    }

    @PutMapping("/{id}")
    public UsuarioResponse actualizar(
        @PathVariable Long id,
        @Valid @RequestBody UsuarioRequest request
    ) {
        return UsuarioResponse.de(service.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id, Authentication authentication) {
        service.eliminar(id, authentication.getName());
    }
}

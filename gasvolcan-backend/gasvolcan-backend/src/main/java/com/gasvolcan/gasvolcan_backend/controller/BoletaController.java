package gasvolcan.controller;

import gasvolcan.dto.*;
import gasvolcan.model.Usuario;
import gasvolcan.repository.UsuarioRepository;
import gasvolcan.service.BoletaService;
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
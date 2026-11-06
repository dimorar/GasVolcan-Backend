package gasvolcan.controller;

import gasvolcan.dto.CategoriaRequest;
import gasvolcan.dto.CategoriaResponse;
import gasvolcan.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/categorias")
public class CategoriaController {

    private final CategoriaService service;

    public CategoriaController(CategoriaService service) {
        this.service = service;
    }

    @GetMapping
    public List<CategoriaResponse> listar() {
        return service.listar();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoriaResponse crear(
        @Valid @RequestBody CategoriaRequest request
    ) {
        return service.crear(request);
    }
}   


package gasvolcan.controller;

import gasvolcan.dto.ProductoRequest;
import gasvolcan.dto.ProductoResponse;
import gasvolcan.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/productos")
public class ProductoController {

    private final ProductoService service;

    public ProductoController(ProductoService service) {
        this.service = service;
    }

    @GetMapping
    public List<ProductoResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ProductoResponse obtener(@PathVariable Long id) {
        return service.obtener(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductoResponse crear(
        @Valid @RequestBody ProductoRequest request
    ) {
        return service.crear(request);
    }

    @PutMapping("/{id}")
    public ProductoResponse actualizar(
        @PathVariable Long id,
        @Valid @RequestBody ProductoRequest request
    ) {
        return service.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}
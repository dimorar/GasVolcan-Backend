package com.gasvolcan.gasvolcan_backend.service;

import com.gasvolcan.gasvolcan_backend.dto.ProductoRequest;
import com.gasvolcan.gasvolcan_backend.dto.ProductoResponse;
import com.gasvolcan.gasvolcan_backend.model.Producto;
import com.gasvolcan.gasvolcan_backend.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository repo;
    private final CategoriaService categorias;

    public ProductoService(
        ProductoRepository repo,
        CategoriaService categorias
    ) {
        this.repo = repo;
        this.categorias = categorias;
    }

    public List<ProductoResponse> listar() {
        return repo.findByActivoTrue()
            .stream().map(this::convertir).toList();
    }

    public ProductoResponse obtener(String codigo) {
        return convertir(buscar(codigo));
    }

    @Transactional
    public ProductoResponse crear(ProductoRequest r) {
        String codigo = r.id() == null || r.id().isBlank() ? null : r.id().trim();
        if (codigo != null && repo.findByCodigo(codigo).isPresent()) {
            throw new IllegalArgumentException("Ya existe un producto con el código " + codigo);
        }

        Producto p = new Producto(
            r.name(),
            r.descripcion(),
            r.price(),
            r.stock(),
            r.img(),
            categorias.obtenerEntidad(r.categoriaId())
        );
        p.setCodigo(codigo);
        if (r.stockCritico() != null) {
            p.setStockCritico(r.stockCritico());
        }

        Producto guardado = repo.save(p);
        if (guardado.getCodigo() == null && guardado.getId() != null) {
            guardado.setCodigo(String.format("G-%02d", guardado.getId()));
        }
        return convertir(guardado);
    }

    @Transactional
    public ProductoResponse actualizar(String codigo, ProductoRequest r) {
        Producto p = buscar(codigo);

        p.setNombre(r.name());
        p.setDescripcion(r.descripcion());
        p.setPrecio(r.price());
        p.setStock(r.stock());
        if (r.stockCritico() != null) {
            p.setStockCritico(r.stockCritico());
        }
        p.setImagenUrl(r.img());
        p.setCategoria(categorias.obtenerEntidad(r.categoriaId()));

        return convertir(p);
    }

    @Transactional
    public void eliminar(String codigo) {
        buscar(codigo).setActivo(false);
    }

    private Producto buscar(String codigo) {
        return repo.findByCodigo(codigo)
            .filter(Producto::isActivo)
            .orElseThrow(() ->
                new java.util.NoSuchElementException("Producto no encontrado"));
    }

    private ProductoResponse convertir(Producto p) {
        return new ProductoResponse(
            p.getCodigo(),
            p.getNombre(),
            p.getDescripcion(),
            p.getPrecio(),
            p.getStock(),
            p.getStockCritico(),
            p.getImagenUrl(),
            p.getCategoria().getId(),
            p.getCategoria().getNombre()
        );
    }
}

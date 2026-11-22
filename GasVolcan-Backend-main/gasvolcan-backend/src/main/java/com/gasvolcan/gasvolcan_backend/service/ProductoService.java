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

    public ProductoResponse obtener(Long id) {
        Producto p = repo.findById(id)
            .filter(Producto::isActivo)
            .orElseThrow(() ->
                new java.util.NoSuchElementException("Producto no encontrado"));

        return convertir(p);
    }

    public ProductoResponse crear(ProductoRequest r) {
        Producto p = new Producto(
            r.nombre(),
            r.descripcion(),
            r.precio(),
            r.stock(),
            r.imagenUrl(),
            categorias.obtenerEntidad(r.categoriaId())
        );

        return convertir(repo.save(p));
    }

    @Transactional
    public ProductoResponse actualizar(Long id, ProductoRequest r) {
        Producto p = repo.findById(id)
            .filter(Producto::isActivo)
            .orElseThrow(() ->
                new java.util.NoSuchElementException("Producto no encontrado"));

        p.setNombre(r.nombre());
        p.setDescripcion(r.descripcion());
        p.setPrecio(r.precio());
        p.setStock(r.stock());
        p.setImagenUrl(r.imagenUrl());
        p.setCategoria(categorias.obtenerEntidad(r.categoriaId()));

        return convertir(p);
    }

    @Transactional
    public void eliminar(Long id) {
        Producto p = repo.findById(id)
            .filter(Producto::isActivo)
            .orElseThrow(() ->
                new java.util.NoSuchElementException("Producto no encontrado"));

        p.setActivo(false);
    }

    private ProductoResponse convertir(Producto p) {
        return new ProductoResponse(
            p.getId(),
            p.getNombre(),
            p.getDescripcion(),
            p.getPrecio(),
            p.getStock(),
            p.getImagenUrl(),
            p.getCategoria().getId(),
            p.getCategoria().getNombre()
        );
    }
}
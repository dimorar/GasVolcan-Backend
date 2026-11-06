package com.gasvolcan.gasvolcan_backend.service;

import com.gasvolcan.gasvolcan_backend.dto.CategoriaRequest;
import com.gasvolcan.gasvolcan_backend.dto.CategoriaResponse;
import com.gasvolcan.gasvolcan_backend.model.Categoria;
import com.gasvolcan.gasvolcan_backend.repository.CategoriaRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository repo;

    public CategoriaService(CategoriaRepository repo) {
        this.repo = repo;
    }

    public List<CategoriaResponse> listar() {
        return repo.findAll().stream()
            .map(c -> new CategoriaResponse(c.getId(), c.getNombre()))
            .toList();
    }

    public CategoriaResponse crear(CategoriaRequest request) {
        if (repo.existsByNombreIgnoreCase(request.nombre())) {
            throw new IllegalArgumentException("La categoría ya existe");
        }

        Categoria categoria = repo.save(new Categoria(request.nombre()));

        return new CategoriaResponse(
            categoria.getId(),
            categoria.getNombre()
        );
    }

    public Categoria obtenerEntidad(Long id) {
        return repo.findById(id)
            .orElseThrow(() ->
                new java.util.NoSuchElementException("Categoría no encontrada"));
    }
}
package com.gasvolcan.gasvolcan_backend.repository;

import com.gasvolcan.gasvolcan_backend.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    boolean existsByNombreIgnoreCase(String nombre);
}

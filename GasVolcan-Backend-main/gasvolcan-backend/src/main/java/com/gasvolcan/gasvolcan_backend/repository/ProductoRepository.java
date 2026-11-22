package com.gasvolcan.gasvolcan_backend.repository;

import com.gasvolcan.gasvolcan_backend.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByActivoTrue();
}

package com.gasvolcan.gasvolcan_backend.repository;

import com.gasvolcan.gasvolcan_backend.model.Boleta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BoletaRepository extends JpaRepository<Boleta, Long> {
    List<Boleta> findByUsuarioIdOrderByFechaDesc(Long usuarioId);
}
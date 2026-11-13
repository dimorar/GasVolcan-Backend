package com.gasvolcan.gasvolcan_backend.repository;

import com.gasvolcan.gasvolcan_backend.model.DetalleBoleta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetalleBoletaRepository extends JpaRepository<DetalleBoleta, Long> {
}

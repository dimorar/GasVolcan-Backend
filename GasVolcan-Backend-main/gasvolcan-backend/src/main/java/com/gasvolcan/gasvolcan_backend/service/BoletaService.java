package com.gasvolcan.gasvolcan_backend.service;

import com.gasvolcan.gasvolcan_backend.dto.*;
import com.gasvolcan.gasvolcan_backend.model.*;
import com.gasvolcan.gasvolcan_backend.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class BoletaService {

    private final BoletaRepository boletas;
    private final ProductoRepository productos;
    private final UsuarioRepository usuarios;

    public BoletaService(
        BoletaRepository boletas,
        ProductoRepository productos,
        UsuarioRepository usuarios
    ) {
        this.boletas = boletas;
        this.productos = productos;
        this.usuarios = usuarios;
    }

    @Transactional
    public BoletaResponse crear(Long usuarioId, BoletaRequest request) {

        Usuario usuario = usuarios.findById(usuarioId)
            .orElseThrow(() ->
                new java.util.NoSuchElementException("Usuario no encontrado"));

        Boleta boleta = new Boleta(usuario);
        BigDecimal total = BigDecimal.ZERO;

        List<BoletaLineaResponse> lineas = new ArrayList<>();

        for (BoletaItemRequest item : request.items()) {

            Producto producto = productos.findById(item.productoId())
                .filter(Producto::isActivo)
                .orElseThrow(() ->
                    new java.util.NoSuchElementException("Producto no encontrado"));

            if (producto.getStock() == null
                || producto.getStock() < item.cantidad()) {
                throw new IllegalArgumentException("Stock insuficiente");
            }

            BigDecimal precio = producto.getPrecio();

            BigDecimal subtotal = precio.multiply(
                BigDecimal.valueOf(item.cantidad())
            );

            producto.setStock(
                producto.getStock() - item.cantidad()
            );

            boleta.agregarDetalle(
                new DetalleBoleta(
                    producto,
                    item.cantidad(),
                    precio,
                    subtotal
                )
            );

            total = total.add(subtotal);

            lineas.add(
                new BoletaLineaResponse(
                    producto.getId(),
                    producto.getNombre(),
                    item.cantidad(),
                    precio,
                    subtotal
                )
            );
        }

        boleta.setTotal(total);

        Boleta guardada = boletas.save(boleta);

        return new BoletaResponse(
            guardada.getId(),
            guardada.getFecha(),
            guardada.getTotal(),
            usuario.getId(),
            lineas
        );
    }
}
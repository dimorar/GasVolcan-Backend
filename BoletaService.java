package com.gasvolcan.gasvolcan_backend.service;

import com.gasvolcan.gasvolcan_backend.dto.*;
import com.gasvolcan.gasvolcan_backend.model.*;
import com.gasvolcan.gasvolcan_backend.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
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
        boleta.setDireccion(request.direccion().trim());
        BigDecimal total = BigDecimal.ZERO;

        for (BoletaItemRequest item : request.items()) {

            Producto producto = productos.findByCodigo(item.id())
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
        }

        boleta.setTotal(total);

        return convertir(boletas.save(boleta));
    }

    @Transactional(readOnly = true)
    public List<BoletaResponse> listar() {
        return boletas.findAllByOrderByFechaDesc()
            .stream().map(this::convertir).toList();
    }

    @Transactional(readOnly = true)
    public List<BoletaResponse> listarPorUsuario(Long usuarioId) {
        return boletas.findByUsuarioIdOrderByFechaDesc(usuarioId)
            .stream().map(this::convertir).toList();
    }

    @Transactional
    public BoletaResponse cambiarEstado(String codigo, String nuevoEstado) {
        EstadoPedido estado = EstadoPedido.desdeEtiqueta(nuevoEstado);
        Boleta boleta = boletas.findById(idDe(codigo))
            .orElseThrow(() ->
                new java.util.NoSuchElementException("Pedido no encontrado"));

        if (boleta.getEstado().esFinal()) {
            throw new IllegalArgumentException(
                "El pedido " + codigo + " está "
                    + boleta.getEstado().etiqueta().toLowerCase()
                    + " y no se puede modificar."
            );
        }

        if (estado == EstadoPedido.CANCELADO) {
            for (DetalleBoleta d : boleta.getDetalles()) {
                Producto p = d.getProducto();
                p.setStock(p.getStock() + d.getCantidad());
            }
        }

        boleta.setEstado(estado);
        return convertir(boleta);
    }

    private Long idDe(String codigo) {
        try {
            return Long.valueOf(codigo.replaceFirst("^P-", ""));
        } catch (NumberFormatException e) {
            throw new java.util.NoSuchElementException("Pedido no encontrado");
        }
    }

    private BoletaResponse convertir(Boleta b) {
        List<BoletaLineaResponse> lineas = b.getDetalles().stream()
            .map(d -> new BoletaLineaResponse(
                d.getProducto().getCodigo(),
                d.getProducto().getNombre(),
                d.getCantidad(),
                d.getPrecioUnitario(),
                d.getSubtotal()
            ))
            .toList();

        return new BoletaResponse(
            String.format("P-%04d", b.getId()),
            b.getFecha(),
            b.getTotal(),
            b.getUsuario().getId(),
            b.getUsuario().getNombre(),
            b.getUsuario().getComuna(),
            b.getDireccion(),
            b.getEstado().etiqueta(),
            lineas
        );
    }
}

package com.gasvolcan.gasvolcan_backend.service;

import com.gasvolcan.gasvolcan_backend.dto.BoletaItemRequest;
import com.gasvolcan.gasvolcan_backend.dto.BoletaRequest;
import com.gasvolcan.gasvolcan_backend.model.*;
import com.gasvolcan.gasvolcan_backend.repository.BoletaRepository;
import com.gasvolcan.gasvolcan_backend.repository.ProductoRepository;
import com.gasvolcan.gasvolcan_backend.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BoletaServiceTest {

    @Mock BoletaRepository boletas;
    @Mock ProductoRepository productos;
    @Mock UsuarioRepository usuarios;
    @InjectMocks BoletaService service;

    @Test
    void rechazaPedidoSinStock() {
        Usuario u = new Usuario("Ana", "ana@duoc.cl", "hash", Rol.CLIENTE);
        Producto p = new Producto("Gas", "", new BigDecimal("10000"), 1, null, new Categoria("Gas"));
        when(usuarios.findById(1L)).thenReturn(Optional.of(u));
        when(productos.findByCodigo("G-05")).thenReturn(Optional.of(p));

        BoletaRequest r = new BoletaRequest("Calle 123", List.of(new BoletaItemRequest("G-05", 2)));

        assertThrows(IllegalArgumentException.class, () -> service.crear(1L, r));
    }

    @Test
    void cancelarPedidoRepoeStock() {
        Usuario u = new Usuario("Ana", "ana@duoc.cl", "hash", Rol.CLIENTE);
        Producto p = new Producto("Gas", "", new BigDecimal("10000"), 1, null, new Categoria("Gas"));
        Boleta b = new Boleta(u);
        b.agregarDetalle(new DetalleBoleta(p, 2, new BigDecimal("10000"), new BigDecimal("20000")));
        when(boletas.findById(5L)).thenReturn(Optional.of(b));

        service.cambiarEstado("P-0005", "Cancelado");

        assertEquals(3, p.getStock());
        assertEquals(EstadoPedido.CANCELADO, b.getEstado());
    }

    @Test
    void noModificaPedidoFinal() {
        Usuario u = new Usuario("Ana", "ana@duoc.cl", "hash", Rol.CLIENTE);
        Boleta b = new Boleta(u);
        b.setEstado(EstadoPedido.ENTREGADO);
        when(boletas.findById(5L)).thenReturn(Optional.of(b));

        assertThrows(IllegalArgumentException.class,
            () -> service.cambiarEstado("P-0005", "Cancelado"));
    }
}

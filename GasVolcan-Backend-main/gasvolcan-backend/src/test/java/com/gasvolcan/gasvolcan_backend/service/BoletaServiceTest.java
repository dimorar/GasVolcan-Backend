package com.gasvolcan.gasvolcan_backend.service;

import com.gasvolcan.gasvolcan_backend.dto.BoletaItemRequest;
import com.gasvolcan.gasvolcan_backend.dto.BoletaRequest;
import com.gasvolcan.gasvolcan_backend.dto.BoletaResponse;
import com.gasvolcan.gasvolcan_backend.model.Boleta;
import com.gasvolcan.gasvolcan_backend.model.Categoria;
import com.gasvolcan.gasvolcan_backend.model.Producto;
import com.gasvolcan.gasvolcan_backend.model.Rol;
import com.gasvolcan.gasvolcan_backend.model.Usuario;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BoletaServiceTest {

    @Mock BoletaRepository boletas;
    @Mock ProductoRepository productos;
    @Mock UsuarioRepository usuarios;
    @InjectMocks BoletaService service;

    private Usuario usuario() {
        return new Usuario("Ana", "ana@correo.cl", "hash", Rol.CLIENTE);
    }

    private Producto producto(int stock) {
        return new Producto("Gas", "", new BigDecimal("10000"), stock, null, new Categoria("Gas"));
    }

    @Test
    void rechazaPedidoSinStock() {
        when(usuarios.findById(1L)).thenReturn(Optional.of(usuario()));
        when(productos.findById(2L)).thenReturn(Optional.of(producto(1)));

        BoletaRequest r = new BoletaRequest(List.of(new BoletaItemRequest(2L, 2)));

        assertThrows(IllegalArgumentException.class, () -> service.crear(1L, r));
    }

    @Test
    void creaBoletaCalculaTotalYDescuentaStock() {
        Producto p = producto(10);
        when(usuarios.findById(1L)).thenReturn(Optional.of(usuario()));
        when(productos.findById(2L)).thenReturn(Optional.of(p));
        when(boletas.save(any(Boleta.class))).thenAnswer(i -> i.getArgument(0));

        BoletaResponse r = service.crear(1L,
            new BoletaRequest(List.of(new BoletaItemRequest(2L, 2))));

        assertEquals(0, new BigDecimal("20000").compareTo(r.total()));
        assertEquals(1, r.detalles().size());
        assertEquals(8, p.getStock());
    }
}

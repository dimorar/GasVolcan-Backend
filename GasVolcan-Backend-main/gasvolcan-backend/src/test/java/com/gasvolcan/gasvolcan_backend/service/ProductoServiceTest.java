package com.gasvolcan.gasvolcan_backend.service;

import com.gasvolcan.gasvolcan_backend.dto.ProductoRequest;
import com.gasvolcan.gasvolcan_backend.model.Categoria;
import com.gasvolcan.gasvolcan_backend.model.Producto;
import com.gasvolcan.gasvolcan_backend.repository.ProductoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

    @Mock ProductoRepository repo;
    @Mock CategoriaService categorias;
    @InjectMocks ProductoService service;

    @Test
    void crearProducto() {
        Categoria cat = new Categoria("Gas");
        when(categorias.obtenerEntidad(1L)).thenReturn(cat);
        when(repo.save(any(Producto.class))).thenAnswer(i -> i.getArgument(0));

        var r = service.crear(new ProductoRequest(
            "Gas 15 kg", "Cilindro", new BigDecimal("25000"), 10, null, 1L));

        assertEquals("Gas 15 kg", r.nombre());
        assertEquals("Gas", r.categoria());
        verify(repo).save(any(Producto.class));
    }

    @Test
    void eliminarHaceBajaLogica() {
        Producto p = new Producto("Gas", "", new BigDecimal("10000"), 5, null, new Categoria("Gas"));
        when(repo.findById(1L)).thenReturn(Optional.of(p));

        service.eliminar(1L);

        assertFalse(p.isActivo());
    }

    @Test
    void obtenerProductoInexistenteLanzaExcepcion() {
        when(repo.findById(9L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> service.obtener(9L));
    }
}

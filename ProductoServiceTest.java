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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
            "G-15", "Gas 15 kg", "Cilindro", new BigDecimal("25000"), 10, 4, null, 1L));

        assertEquals("G-15", r.id());
        assertEquals("Gas 15 kg", r.name());
        assertEquals(4, r.stockCritico());
        verify(repo).save(any(Producto.class));
    }
}

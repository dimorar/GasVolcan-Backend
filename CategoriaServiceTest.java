package com.gasvolcan.gasvolcan_backend.service;

import com.gasvolcan.gasvolcan_backend.dto.CategoriaRequest;
import com.gasvolcan.gasvolcan_backend.model.Categoria;
import com.gasvolcan.gasvolcan_backend.repository.CategoriaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoriaServiceTest {

    @Mock CategoriaRepository repo;
    @InjectMocks CategoriaService service;

    @Test
    void crearCategoriaNueva() {
        when(repo.existsByNombreIgnoreCase("Cilindros")).thenReturn(false);
        when(repo.save(any(Categoria.class))).thenAnswer(i -> i.getArgument(0));

        assertEquals("Cilindros", service.crear(new CategoriaRequest("Cilindros")).nombre());
        verify(repo).save(any(Categoria.class));
    }

    @Test
    void rechazaCategoriaDuplicada() {
        when(repo.existsByNombreIgnoreCase("Cilindros")).thenReturn(true);

        assertThrows(IllegalArgumentException.class,
            () -> service.crear(new CategoriaRequest("Cilindros")));
    }
}

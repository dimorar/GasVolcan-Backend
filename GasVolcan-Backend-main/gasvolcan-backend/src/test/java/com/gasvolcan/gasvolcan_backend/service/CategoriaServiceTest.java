package com.gasvolcan.gasvolcan_backend.service;

import com.gasvolcan.gasvolcan_backend.dto.CategoriaRequest;
import com.gasvolcan.gasvolcan_backend.model.Categoria;
import com.gasvolcan.gasvolcan_backend.repository.CategoriaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

        verify(repo, never()).save(any(Categoria.class));
    }

    @Test
    void obtenerEntidadInexistenteLanzaExcepcion() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(java.util.NoSuchElementException.class, () -> service.obtenerEntidad(99L));
    }
}

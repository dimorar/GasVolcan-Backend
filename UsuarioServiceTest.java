package com.gasvolcan.gasvolcan_backend.service;

import com.gasvolcan.gasvolcan_backend.model.Rol;
import com.gasvolcan.gasvolcan_backend.model.Usuario;
import com.gasvolcan.gasvolcan_backend.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock UsuarioRepository repo;
    @Mock PasswordEncoder encoder;
    @InjectMocks UsuarioService service;

    @Test
    void registroAsignaRolCliente() {
        when(repo.existsByEmail("cliente@duoc.cl")).thenReturn(false);
        when(encoder.encode("clave1234")).thenReturn("hash");
        when(repo.save(any(Usuario.class))).thenAnswer(i -> i.getArgument(0));

        Usuario u = service.registrarCliente(
            "12345678-5", "Cliente", "Cliente@duoc.cl", "clave1234", "RM", "Santiago");

        assertEquals(Rol.CLIENTE, u.getRol());
        assertEquals("hash", u.getPassword());
        assertEquals("cliente@duoc.cl", u.getEmail());
    }

    @Test
    void rechazaCorreoExistente() {
        when(repo.existsByEmail("cliente@duoc.cl")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> service.registrarCliente(
            "12345678-5", "Cliente", "cliente@duoc.cl", "clave1234", "RM", "Santiago"));
    }

    @Test
    void rechazaRunExistente() {
        when(repo.existsByRut("12345678-5")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> service.registrarCliente(
            "12345678-5", "Cliente", "nuevo@duoc.cl", "clave1234", "RM", "Santiago"));
    }
}

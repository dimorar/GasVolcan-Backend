package com.gasvolcan.gasvolcan_backend.service;

import com.gasvolcan.gasvolcan_backend.dto.AuthResponse;
import com.gasvolcan.gasvolcan_backend.dto.LoginRequest;
import com.gasvolcan.gasvolcan_backend.dto.RegisterRequest;
import com.gasvolcan.gasvolcan_backend.model.Rol;
import com.gasvolcan.gasvolcan_backend.model.Usuario;
import com.gasvolcan.gasvolcan_backend.repository.UsuarioRepository;
import com.gasvolcan.gasvolcan_backend.security.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock UsuarioRepository usuarios;
    @Mock PasswordEncoder encoder;
    @Mock JwtService jwt;
    @InjectMocks AuthService service;

    @Test
    void registroAsignaRolClienteYEncriptaClave() {
        when(usuarios.existsByEmail("cliente@correo.cl")).thenReturn(false);
        when(encoder.encode("clave1234")).thenReturn("hash");
        when(jwt.generar("cliente@correo.cl")).thenReturn("token-demo");

        AuthResponse r = service.registrar(
            new RegisterRequest("Cliente", "cliente@correo.cl", "clave1234"));

        ArgumentCaptor<Usuario> captor = ArgumentCaptor.forClass(Usuario.class);
        verify(usuarios).save(captor.capture());

        assertEquals(Rol.CLIENTE, captor.getValue().getRol());
        assertEquals("hash", captor.getValue().getPassword());
        assertEquals("CLIENTE", r.rol());
        assertEquals("token-demo", r.token());
        assertEquals("Bearer", r.tokenType());
    }

    @Test
    void registroRechazaCorreoExistente() {
        when(usuarios.existsByEmail("cliente@correo.cl")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () ->
            service.registrar(new RegisterRequest("Cliente", "cliente@correo.cl", "clave1234")));

        verify(usuarios, never()).save(any(Usuario.class));
    }

    @Test
    void loginCorrectoEntregaToken() {
        Usuario u = new Usuario("Ana", "ana@correo.cl", "hash", Rol.CLIENTE);
        when(usuarios.findByEmail("ana@correo.cl")).thenReturn(Optional.of(u));
        when(encoder.matches("clave1234", "hash")).thenReturn(true);
        when(jwt.generar("ana@correo.cl")).thenReturn("token-demo");

        AuthResponse r = service.login(new LoginRequest("ana@correo.cl", "clave1234"));

        assertEquals("token-demo", r.token());
        assertEquals("ana@correo.cl", r.email());
    }

    @Test
    void loginRechazaClaveIncorrecta() {
        Usuario u = new Usuario("Ana", "ana@correo.cl", "hash", Rol.CLIENTE);
        when(usuarios.findByEmail("ana@correo.cl")).thenReturn(Optional.of(u));
        when(encoder.matches("mala", "hash")).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () ->
            service.login(new LoginRequest("ana@correo.cl", "mala")));
    }

    @Test
    void loginRechazaUsuarioInexistente() {
        when(usuarios.findByEmail("nadie@correo.cl")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () ->
            service.login(new LoginRequest("nadie@correo.cl", "clave1234")));
    }
}

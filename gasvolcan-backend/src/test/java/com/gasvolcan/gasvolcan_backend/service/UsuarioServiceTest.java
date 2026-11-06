package cl.gasvolcan.service;
 
import cl.gasvolcan.model.Usuario;
import cl.gasvolcan.model.Rol;
import cl.gasvolcan.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
 
@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {
 @Mock UsuarioRepository repo; @Mock PasswordEncoder encoder;
 @InjectMocks UsuarioService service;
 @Test void registroAsignaRolCliente(){
  when(repo.existsByEmail("cliente@correo.cl")).thenReturn(false);
  when(encoder.encode("clave1234")).thenReturn("hash");
  when(repo.save(any(Usuario.class))).thenAnswer(i->i.getArgument(0));
  Usuario u=service.registrarCliente("Cliente","cliente@correo.cl","clave1234");
  assertEquals(Rol.CLIENTE,u.getRol()); assertEquals("hash",u.getPassword());
 }
 @Test void rechazaCorreoExistente(){
  when(repo.existsByEmail("cliente@correo.cl")).thenReturn(true);
  assertThrows(IllegalStateException.class,()->service.registrarCliente("Cliente","cliente@correo.cl","clave1234"));
 }
}

package cl.gasvolcan.service;
 
import cl.gasvolcan.dto.*;
import cl.gasvolcan.model.*;
import cl.gasvolcan.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
 
@ExtendWith(MockitoExtension.class)
class BoletaServiceTest {
 @Mock BoletaRepository boletas; @Mock ProductoRepository productos; @Mock UsuarioService usuarios;
 @InjectMocks BoletaService service;
 @Test void rechazaPedidoSinStock(){
  Usuario u=new Usuario("Ana","ana@correo.cl","hash",Rol.CLIENTE);
  Producto p=new Producto("Gas","",new BigDecimal("10000"),1,null,new Categoria("Gas"));
  when(usuarios.obtener(1L)).thenReturn(u); when(productos.findById(2L)).thenReturn(Optional.of(p));
  BoletaRequest r=new BoletaRequest(1L,java.util.List.of(new BoletaItemRequest(2L,2)));
  assertThrows(IllegalArgumentException.class,()->service.crear(r));
 }
}

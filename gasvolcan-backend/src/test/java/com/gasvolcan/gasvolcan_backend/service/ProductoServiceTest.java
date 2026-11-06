package cl.gasvolcan.service;
 
import cl.gasvolcan.dto.ProductoRequest;
import cl.gasvolcan.model.Categoria;
import cl.gasvolcan.model.Producto;
import cl.gasvolcan.repository.ProductoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
 
@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {
 @Mock ProductoRepository repo; @Mock CategoriaService categorias;
 @InjectMocks ProductoService service;
 @Test void crearProducto(){
  Categoria cat=new Categoria("Gas");
  when(categorias.obtenerEntidad(1L)).thenReturn(cat);
  when(repo.save(any(Producto.class))).thenAnswer(i->i.getArgument(0));
  var r=service.crear(new ProductoRequest("Gas 15 kg","Cilindro",new BigDecimal("25000"),10,null,1L));
  assertEquals("Gas 15 kg",r.nombre());
  verify(repo).save(any(Producto.class));
 }
}

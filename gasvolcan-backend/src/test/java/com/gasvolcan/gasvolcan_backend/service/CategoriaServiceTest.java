package cl.gasvolcan.service;
 
import cl.gasvolcan.dto.CategoriaRequest;
import cl.gasvolcan.model.Categoria;
import cl.gasvolcan.repository.CategoriaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
 
@ExtendWith(MockitoExtension.class)
class CategoriaServiceTest {
 @Mock CategoriaRepository repo;
 @InjectMocks CategoriaService service;
 @Test void crearCategoriaNueva(){
  when(repo.existsByNombreIgnoreCase("Cilindros")).thenReturn(false);
  when(repo.save(any(Categoria.class))).thenAnswer(i->i.getArgument(0));
  assertEquals("Cilindros",service.crear(new CategoriaRequest("Cilindros")).nombre());
  verify(repo).save(any(Categoria.class));
 }
 @Test void rechazaCategoriaDuplicada(){
  when(repo.existsByNombreIgnoreCase("Cilindros")).thenReturn(true);
  assertThrows(IllegalStateException.class,()->service.crear(new CategoriaRequest("Cilindros")));
 }
}

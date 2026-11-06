package cl.gasvolcan.controller;
 
import cl.gasvolcan.dto.*;
import cl.gasvolcan.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
 
@WebMvcTest(AuthController.class)
class AuthControllerTest {
 @Autowired MockMvc mvc; @Autowired ObjectMapper mapper; @MockBean AuthService service;
 @Test void loginRespondeToken() throws Exception {
  when(service.login(any())).thenReturn(new AuthResponse("token-demo","Bearer",1L,"Ana","ana@correo.cl","CLIENTE"));
  mvc.perform(post("/api/v1/auth/login").contentType(MediaType.APPLICATION_JSON)
   .content(mapper.writeValueAsString(new LoginRequest("ana@correo.cl","clave1234"))))
   .andExpect(status().isOk()).andExpect(jsonPath("$.token").value("token-demo"));
 }
}

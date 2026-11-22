package com.gasvolcan.gasvolcan_backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gasvolcan.gasvolcan_backend.dto.AuthResponse;
import com.gasvolcan.gasvolcan_backend.dto.LoginRequest;
import com.gasvolcan.gasvolcan_backend.exception.GlobalExceptionHandler;
import com.gasvolcan.gasvolcan_backend.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock AuthService service;
    @InjectMocks AuthController controller;

    MockMvc mvc;
    final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(controller)
            .setControllerAdvice(new GlobalExceptionHandler())
            .build();
    }

    @Test
    void loginResponde200ConToken() throws Exception {
        when(service.login(any())).thenReturn(
            new AuthResponse("token-demo", "Bearer", 1L, "Ana", "ana@correo.cl", "CLIENTE"));

        mvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new LoginRequest("ana@correo.cl", "clave1234"))))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").value("token-demo"));
    }

    @Test
    void loginConEmailInvalidoResponde400() throws Exception {
        mvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new LoginRequest("no-es-email", "clave1234"))))
            .andExpect(status().isBadRequest());
    }
}

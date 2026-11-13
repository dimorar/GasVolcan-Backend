package com.gasvolcan.gasvolcan_backend.controller;

import com.gasvolcan.gasvolcan_backend.dto.AuthResponse;
import com.gasvolcan.gasvolcan_backend.dto.LoginRequest;
import com.gasvolcan.gasvolcan_backend.service.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock AuthService service;
    @InjectMocks AuthController controller;

    @Test
    void loginRespondeToken() {
        when(service.login(any())).thenReturn(
            new AuthResponse("token-demo", "Bearer", 1L, "Ana", "ana@duoc.cl", "CLIENTE"));

        AuthResponse r = controller.login(new LoginRequest("ana@duoc.cl", "clave1234"));

        assertEquals("token-demo", r.token());
    }
}

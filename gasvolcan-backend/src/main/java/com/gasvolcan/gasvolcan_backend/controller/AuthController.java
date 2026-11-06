package com.gasvolcan.gasvolcan_backend.controller;

import com.gasvolcan.gasvolcan_backend.dto.*;
import com.gasvolcan.gasvolcan_backend.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/registro")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthResponse registrar(
        @Valid @RequestBody RegisterRequest request
    ) {
        return service.registrar(request);
    }

    @PostMapping("/login")
    public AuthResponse login(
        @Valid @RequestBody LoginRequest request
    ) {
        return service.login(request);
    }
}
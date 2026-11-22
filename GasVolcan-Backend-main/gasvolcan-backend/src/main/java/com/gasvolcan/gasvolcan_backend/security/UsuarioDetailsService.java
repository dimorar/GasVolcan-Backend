package com.gasvolcan.gasvolcan_backend.security;

import com.gasvolcan.gasvolcan_backend.model.Usuario;
import com.gasvolcan.gasvolcan_backend.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class UsuarioDetailsService implements UserDetailsService {

    private final UsuarioRepository repo;

    public UsuarioDetailsService(UsuarioRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        Usuario u = repo.findByEmail(email)
            .orElseThrow(() ->
                new UsernameNotFoundException("Usuario no encontrado"));

        return User.withUsername(u.getEmail())
            .password(u.getPassword())
            .roles(u.getRol().name())
            .disabled(!u.isActivo())
            .build();
    }
}
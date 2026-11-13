package com.gasvolcan.gasvolcan_backend.service;

import com.gasvolcan.gasvolcan_backend.dto.UsuarioRequest;
import com.gasvolcan.gasvolcan_backend.model.Rol;
import com.gasvolcan.gasvolcan_backend.model.Usuario;
import com.gasvolcan.gasvolcan_backend.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UsuarioService {

    private final UsuarioRepository repo;
    private final PasswordEncoder encoder;

    public UsuarioService(UsuarioRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    public List<Usuario> listar() {
        return repo.findByActivoTrue();
    }

    public Usuario obtener(Long id) {
        return repo.findById(id)
            .filter(Usuario::isActivo)
            .orElseThrow(() -> new NoSuchElementException("Usuario no encontrado"));
    }

    public Usuario registrarCliente(String rut, String nombre, String email,
                                    String password, String region, String comuna) {
        String correo = email.trim().toLowerCase();
        String run = rut.trim().toUpperCase();

        if (repo.existsByEmail(correo)) {
            throw new IllegalArgumentException("El correo ya está registrado");
        }
        if (repo.existsByRut(run)) {
            throw new IllegalArgumentException("El RUN ya está registrado");
        }

        Usuario u = new Usuario(
            nombre.trim(), correo, encoder.encode(password), Rol.CLIENTE
        );
        u.setRut(run);
        u.setRegion(region);
        u.setComuna(comuna);
        return repo.save(u);
    }

    @Transactional
    public Usuario actualizar(Long id, UsuarioRequest r) {
        Usuario u = obtener(id);
        String correo = r.email().trim().toLowerCase();
        String run = r.rut().trim().toUpperCase();

        repo.findByEmail(correo)
            .filter(otro -> !otro.getId().equals(id))
            .ifPresent(otro -> {
                throw new IllegalArgumentException("El correo ya está registrado");
            });
        repo.findByRut(run)
            .filter(otro -> !otro.getId().equals(id))
            .ifPresent(otro -> {
                throw new IllegalArgumentException("El RUN ya está registrado");
            });

        u.setRut(run);
        u.setNombre(r.nombre().trim());
        u.setEmail(correo);
        u.setRegion(r.region());
        u.setComuna(r.comuna());
        if (r.password() != null && !r.password().isBlank()) {
            u.setPassword(encoder.encode(r.password()));
        }
        return u;
    }

    @Transactional
    public void eliminar(Long id, String emailActual) {
        Usuario u = obtener(id);
        if (u.getEmail().equalsIgnoreCase(emailActual)) {
            throw new IllegalArgumentException("No puedes eliminar tu propia cuenta");
        }
        u.setActivo(false);
    }
}

package gasvolcan.service;

import gasvolcan.dto.*;
import gasvolcan.model.Rol;
import gasvolcan.model.Usuario;
import gasvolcan.repository.UsuarioRepository;
import gasvolcan.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarios;
    private final PasswordEncoder encoder;
    private final JwtService jwt;

    public AuthService(
        UsuarioRepository usuarios,
        PasswordEncoder encoder,
        JwtService jwt
    ) {
        this.usuarios = usuarios;
        this.encoder = encoder;
        this.jwt = jwt;
    }

    public AuthResponse registrar(RegisterRequest r) {
        if (usuarios.existsByEmail(r.email())) {
            throw new IllegalArgumentException(
                "El correo ya está registrado"
            );
        }

        Usuario u = new Usuario(
            r.nombre(),
            r.email(),
            encoder.encode(r.password()),
            Rol.CLIENTE
        );

        usuarios.save(u);
        return respuesta(u);
    }

    public AuthResponse login(LoginRequest r) {
        Usuario u = usuarios.findByEmail(r.email())
            .orElseThrow(() ->
                new IllegalArgumentException("Credenciales incorrectas"));

        if (!u.isActivo()
            || !encoder.matches(r.password(), u.getPassword())) {
            throw new IllegalArgumentException(
                "Credenciales incorrectas"
            );
        }

        return respuesta(u);
    }

    private AuthResponse respuesta(Usuario u) {
        return new AuthResponse(
            jwt.generar(u.getEmail()),
            "Bearer",
            u.getId(),
            u.getNombre(),
            u.getEmail(),
            u.getRol().name()
        );
    }
}
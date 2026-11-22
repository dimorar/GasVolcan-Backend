package com.gasvolcan.gasvolcan_backend.config;

import com.gasvolcan.gasvolcan_backend.model.Categoria;
import com.gasvolcan.gasvolcan_backend.model.Producto;
import com.gasvolcan.gasvolcan_backend.model.Rol;
import com.gasvolcan.gasvolcan_backend.model.Usuario;
import com.gasvolcan.gasvolcan_backend.repository.CategoriaRepository;
import com.gasvolcan.gasvolcan_backend.repository.ProductoRepository;
import com.gasvolcan.gasvolcan_backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarios;
    private final CategoriaRepository categorias;
    private final ProductoRepository productos;
    private final PasswordEncoder encoder;
    private final String adminPassword;

    public DataInitializer(
        UsuarioRepository usuarios,
        CategoriaRepository categorias,
        ProductoRepository productos,
        PasswordEncoder encoder,
        @Value("${app.admin.password:admin123}") String adminPassword
    ) {
        this.usuarios = usuarios;
        this.categorias = categorias;
        this.productos = productos;
        this.encoder = encoder;
        this.adminPassword = adminPassword;
    }

    @Override
    public void run(String... args) {
        if (!usuarios.existsByEmail("admin@duoc.cl")) {
            usuarios.save(new Usuario(
                "Administrador",
                "admin@duoc.cl",
                encoder.encode(adminPassword),
                Rol.ADMINISTRADOR
            ));
        }

        if (productos.count() == 0) {
            Categoria cilindros = categorias.save(new Categoria("Cilindros"));

            productos.save(new Producto("Cilindro de gas 5 kg", "Cilindro de gas licuado de 5 kg",
                new BigDecimal("11000"), 50, null, cilindros));
            productos.save(new Producto("Cilindro de gas 11 kg", "Cilindro de gas licuado de 11 kg",
                new BigDecimal("20000"), 50, null, cilindros));
            productos.save(new Producto("Cilindro de gas 15 kg", "Cilindro de gas licuado de 15 kg",
                new BigDecimal("27000"), 50, null, cilindros));
            productos.save(new Producto("Cilindro de gas 45 kg", "Cilindro de gas licuado de 45 kg",
                new BigDecimal("78000"), 20, null, cilindros));
        }
    }
}

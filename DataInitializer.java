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
        @Value("${app.admin.password}") String adminPassword
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
            Usuario admin = new Usuario(
                "Admin Sistema", "admin@duoc.cl",
                encoder.encode(adminPassword), Rol.ADMINISTRADOR
            );
            admin.setRut("12345678-5");
            admin.setRegion("RM");
            admin.setComuna("Santiago");
            usuarios.save(admin);
        }

        productos.findAll().stream()
            .filter(p -> p.getCodigo() == null)
            .forEach(p -> {
                p.setCodigo(String.format("G-%02d", p.getId()));
                productos.save(p);
            });

        if (productos.count() == 0) {
            Categoria gas = categorias.findAll().stream().findFirst()
                .orElseGet(() -> categorias.save(new Categoria("Gas")));

            crearProducto("G-05", "Gas 5Kg", "10500", 15, 5, "img/gas-5kg.svg", gas);
            crearProducto("G-11", "Gas 11Kg", "16800", 3, 5, "img/gas-11kg.svg", gas);
            crearProducto("G-15", "Gas 15Kg", "21500", 20, 5, "img/gas-15kg.svg", gas);
            crearProducto("G-45", "Gas 45Kg", "62000", 2, 3, "img/gas-45kg.svg", gas);
        }
    }

    private void crearProducto(String codigo, String nombre, String precio, int stock,
                               int stockCritico, String img, Categoria categoria) {
        Producto p = new Producto(
            nombre, "Cilindro de " + nombre, new BigDecimal(precio),
            stock, img, categoria
        );
        p.setCodigo(codigo);
        p.setStockCritico(stockCritico);
        productos.save(p);
    }
}

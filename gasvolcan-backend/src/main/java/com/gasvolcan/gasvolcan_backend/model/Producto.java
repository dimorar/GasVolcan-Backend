package com.gasvolcan.gasvolcan_backend.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    private String descripcion;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    @Column(nullable = false)
    private Integer stock;

    private String imagenUrl;

    @ManyToOne(optional = false)
    private Categoria categoria;

    private boolean activo = true;

    protected Producto() {}

    public Producto(String nombre, String descripcion, BigDecimal precio,
                    Integer stock, String imagenUrl, Categoria categoria) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.imagenUrl = imagenUrl;
        this.categoria = categoria;
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public BigDecimal getPrecio() { return precio; }
    public Integer getStock() { return stock; }
    public String getImagenUrl() { return imagenUrl; }
    public Categoria getCategoria() { return categoria; }
    public boolean isActivo() { return activo; }

    public void setNombre(String v) { nombre = v; }
    public void setDescripcion(String v) { descripcion = v; }
    public void setPrecio(BigDecimal v) { precio = v; }
    public void setStock(Integer v) { stock = v; }
    public void setImagenUrl(String v) { imagenUrl = v; }
    public void setCategoria(Categoria v) { categoria = v; }
    public void setActivo(boolean v) { activo = v; }
}

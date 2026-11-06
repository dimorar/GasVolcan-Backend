package com.gasvolcan.gasvolcan_backend.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "boletas")
public class Boleta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fecha;
    private BigDecimal total;

    @ManyToOne
    private Usuario usuario;

    @OneToMany(mappedBy = "boleta", cascade = CascadeType.ALL)
    private List<DetalleBoleta> detalles = new ArrayList<>();

    protected Boleta() {}

    public Boleta(Usuario usuario) {
        this.usuario = usuario;
        this.fecha = LocalDateTime.now();
        this.total = BigDecimal.ZERO;
    }

    public void agregarDetalle(DetalleBoleta detalle) {
        detalles.add(detalle);
        detalle.setBoleta(this);
    }

    public Long getId() { return id; }
    public LocalDateTime getFecha() { return fecha; }
    public BigDecimal getTotal() { return total; }
    public Usuario getUsuario() { return usuario; }
    public List<DetalleBoleta> getDetalles() { return detalles; }
    public void setTotal(BigDecimal total) { this.total = total; }
}
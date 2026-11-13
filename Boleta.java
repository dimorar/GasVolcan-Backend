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
    private String direccion;

    @Enumerated(EnumType.STRING)
    private EstadoPedido estado = EstadoPedido.PENDIENTE;

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
    public String getDireccion() { return direccion; }
    public EstadoPedido getEstado() { return estado == null ? EstadoPedido.PENDIENTE : estado; }
    public Usuario getUsuario() { return usuario; }
    public List<DetalleBoleta> getDetalles() { return detalles; }
    public void setTotal(BigDecimal total) { this.total = total; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public void setEstado(EstadoPedido estado) { this.estado = estado; }
}

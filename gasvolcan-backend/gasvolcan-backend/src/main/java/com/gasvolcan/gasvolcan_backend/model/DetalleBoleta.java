package gasvolcan.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "detalles_boleta")
public class DetalleBoleta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Boleta boleta;

    @ManyToOne
    private Producto producto;

    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;

    protected DetalleBoleta() {}

    public DetalleBoleta(Producto producto, Integer cantidad,
                         BigDecimal precioUnitario, BigDecimal subtotal) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
    }

    public void setBoleta(Boleta boleta) { this.boleta = boleta; }
    public Long getId() { return id; }
    public Producto getProducto() { return producto; }
    public Integer getCantidad() { return cantidad; }
    public BigDecimal getSubtotal() { return subtotal; }
}
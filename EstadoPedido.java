package com.gasvolcan.gasvolcan_backend.model;

public enum EstadoPedido {
    PENDIENTE("Pendiente"),
    EN_REPARTO("En reparto"),
    ENTREGADO("Entregado"),
    CANCELADO("Cancelado");

    private final String etiqueta;

    EstadoPedido(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String etiqueta() {
        return etiqueta;
    }

    public boolean esFinal() {
        return this == ENTREGADO || this == CANCELADO;
    }

    public static EstadoPedido desdeEtiqueta(String texto) {
        for (EstadoPedido e : values()) {
            if (e.etiqueta.equalsIgnoreCase(texto == null ? "" : texto.trim())) {
                return e;
            }
        }
        throw new IllegalArgumentException("Estado inválido: " + texto);
    }
}

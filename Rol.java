package com.gasvolcan.gasvolcan_backend.model;

public enum Rol {
    ADMINISTRADOR("Admin"),
    VENDEDOR("Vendedor"),
    CLIENTE("Cliente");

    private final String etiqueta;

    Rol(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String etiqueta() {
        return etiqueta;
    }
}

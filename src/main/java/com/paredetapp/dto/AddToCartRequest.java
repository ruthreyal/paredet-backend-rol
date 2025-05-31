package com.paredetapp.dto;

import java.util.UUID;

public class AddToCartRequest {
    private UUID productoId;
    private int cantidad;

    public UUID getProductoId() {
        return productoId;
    }

    public void setProductoId(UUID productoId) {
        this.productoId = productoId;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}


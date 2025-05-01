package com.paredetapp.model;

import jakarta.persistence.*;
import lombok.Data;

import jakarta.servlet.http.HttpServletRequest;

import java.util.UUID;

/**
 * Entidad que representa un registro en el carrito de compras.
 * En lugar de almacenar directamente los UUID de usuario y producto,
 * se utiliza la relación con las entidades correspondientes.
 */

@Data
@Entity
@Table(name = "carrito")
public class Carrito {

    @Id
    @GeneratedValue
    private UUID id;

    /**
     * Relación ManyToOne con Usuario.
     * Cada registro del carrito pertenece a un usuario.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    /**
     * Relación ManyToOne con Producto.
     * Cada registro del carrito está asociado a un producto.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    /**
     * Cantidad del producto en el carrito.
     */
    @Column(nullable = false)
    private int cantidad;

    // Getters y Setters

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public UUID getUsuarioId() {
        return usuario != null ? usuario.getId() : null;
    }

}



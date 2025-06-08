package com.paredetapp.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(
        name = "productos",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"nombre"})
        }
)
public class Producto {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, length = 255)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(nullable = false)
    private double precio;

    private double precioRecomendado;

    @Column(nullable = false)
    private int stock;

    @Column(length = 255)
    private String imagenUrl;

    @Column(length = 100)
    private String referencia;

    @Column(length = 100)
    private String formato;

    @Column(length = 100)
    private String tipoAplicacion;

    private double peso;

    @Column(length = 100)
    private String familia;

    @ManyToOne
    @JoinColumn(nullable = false, name = "categoria_id")
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(nullable = false, name = "coleccion_id")
    private Coleccion coleccion;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private Date fechaCreacion;

    @PrePersist
    protected void onCreate() {
        this.fechaCreacion = new Date();
    }
}


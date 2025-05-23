package com.paredetapp.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.UUID;

@Data
@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue
    private UUID id;

    private String nombre;
    private String descripcion;
    private double precio;
    private double precioRecomendado;
    private int stock;

    private String imagenUrl;
    private String referencia;
    private String formato;
    private String tipoAplicacion;
    private double peso;
    private String familia;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "coleccion_id")
    private Coleccion coleccion;
}

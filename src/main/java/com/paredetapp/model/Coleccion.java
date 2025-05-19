package com.paredetapp.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.UUID;

@Data
@Entity
@Table(name = "colecciones")
public class Coleccion {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(unique = true, nullable = false)
    private String nombre;

    @Column(length = 500)
    private String descripcion;

    @Column(name = "imagen_url")
    private String imagenUrl;
}


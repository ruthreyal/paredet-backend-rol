package com.paredetapp.model;

import javax.persistence.*;
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
}

package com.paredetapp.model;

import javax.persistence.*;
import lombok.Data;
import java.util.UUID;

@Data
@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue
    private UUID id;

    private String nombre;
    private String apellido;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String telefono;

    private String direccion;
    private String ciudad;
    private String codigoPostal;
    private String pais;

    @ManyToOne
    @JoinColumn(name = "rol_id")
    private Rol rol;
}


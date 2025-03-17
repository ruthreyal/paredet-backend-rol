package com.paredetapp.model;

import jakarta.persistence.*;
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
    private String contraseña;

    @Column(unique = true, nullable = false)
    private String telefono;

    private String direccion;
    private String ciudad;
    private String codigoPostal;
    private String pais;

    @Enumerated(EnumType.STRING)
    private Rol rol;

    public enum Rol {
        ADMIN, USUARIO
    }
}

package com.paredetapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue
    private UUID id;

    @NotBlank
    @Size(min = 2, max = 50)
    private String nombre;

    @NotBlank
    @Size(min = 2, max = 50)
    private String apellido;

    @Email
    @NotBlank
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank
    @Size(min = 6)
    @Column(nullable = false)
    private String password;

    @NotBlank
    @Pattern(regexp = "\\d{9}")
    @Column(unique = true, nullable = false)
    private String telefono;

    @Size(max = 100)
    private String direccion;

    @Size(max = 50)
    private String ciudad;

    @Size(max = 10)
    private String codigoPostal;

    @Size(max = 50)
    private String pais;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rol_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Rol rol;

    @Size(max = 255)
    private String tokenRecuperacion;
    private LocalDateTime fechaExpiracionToken;
}





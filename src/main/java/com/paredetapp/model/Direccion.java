package com.paredetapp.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "direcciones")
@Data
@NoArgsConstructor
public class Direccion {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(nullable = false)
    private String direccion;

    @Column(nullable = false)
    private String ciudad;

    @Column(nullable = false)
    private String provincia;

    @Column(nullable = false)
    private String cp;

    @Column(nullable = false)
    private String pais;
}




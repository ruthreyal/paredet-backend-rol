package com.paredetapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioAdminDTO {
    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private String telefono;
    private String direccion;
    private String ciudad;
    private String codigoPostal;
    private String pais;
    private String rolNombre; // "USER" o "ADMIN"
}


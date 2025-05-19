package com.paredetapp.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class ColeccionDTO {
    private UUID id;
    private String nombre;
    private String descripcion;
    private String imagenUrl;
}


package com.paredetapp.dto;

import java.util.Date;
import java.util.UUID;
import lombok.Data;

@Data
public class ProductoDTO {
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
    private UUID categoriaId;
    private UUID coleccionId;
    private Date fechaCreacion;

}


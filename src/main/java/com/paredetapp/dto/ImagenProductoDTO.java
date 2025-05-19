package com.paredetapp.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class ImagenProductoDTO {
    private UUID id;
    private UUID productoId;
    private String url;
    private boolean destacada;
}


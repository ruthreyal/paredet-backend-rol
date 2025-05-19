package com.paredetapp.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class FavoritoDTO {
    private UUID id;
    private UUID usuarioId;
    private UUID productoId;
}


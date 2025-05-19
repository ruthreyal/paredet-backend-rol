package com.paredetapp.mapper;

import com.paredetapp.dto.ColeccionDTO;
import com.paredetapp.model.Coleccion;

public class ColeccionMapper {

    public static ColeccionDTO toDTO(Coleccion coleccion) {
        ColeccionDTO dto = new ColeccionDTO();
        dto.setId(coleccion.getId());
        dto.setNombre(coleccion.getNombre());
        dto.setDescripcion(coleccion.getDescripcion());
        dto.setImagenUrl(coleccion.getImagenUrl());
        return dto;
    }

    public static Coleccion toEntity(ColeccionDTO dto) {
        Coleccion coleccion = new Coleccion();
        coleccion.setId(dto.getId());
        coleccion.setNombre(dto.getNombre());
        coleccion.setDescripcion(dto.getDescripcion());
        coleccion.setImagenUrl(dto.getImagenUrl());
        return coleccion;
    }
}

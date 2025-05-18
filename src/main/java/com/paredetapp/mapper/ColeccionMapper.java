package com.paredetapp.mapper;

import com.paredetapp.dto.ColeccionDTO;
import com.paredetapp.model.Coleccion;

public class ColeccionMapper {

    public static ColeccionDTO toDTO(Coleccion coleccion) {
        ColeccionDTO dto = new ColeccionDTO();
        dto.setId(coleccion.getId());
        dto.setNombre(coleccion.getNombre());
        return dto;
    }

    public static Coleccion toEntity(ColeccionDTO dto) {
        Coleccion coleccion = new Coleccion();
        coleccion.setId(dto.getId());
        coleccion.setNombre(dto.getNombre());
        return coleccion;
    }
}
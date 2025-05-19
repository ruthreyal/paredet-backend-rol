package com.paredetapp.mapper;

import com.paredetapp.dto.ImagenProductoDTO;
import com.paredetapp.model.ImagenProducto;
import com.paredetapp.model.Producto;

public class ImagenProductoMapper {

    public static ImagenProductoDTO toDTO(ImagenProducto imagen) {
        ImagenProductoDTO dto = new ImagenProductoDTO();
        dto.setId(imagen.getId());
        dto.setProductoId(imagen.getProducto().getId());
        dto.setUrl(imagen.getUrl());
        dto.setDestacada(imagen.isDestacada());
        return dto;
    }

    public static ImagenProducto toEntity(ImagenProductoDTO dto, Producto producto) {
        ImagenProducto imagen = new ImagenProducto();
        imagen.setId(dto.getId());
        imagen.setProducto(producto);
        imagen.setUrl(dto.getUrl());
        imagen.setDestacada(dto.isDestacada());
        return imagen;
    }
}


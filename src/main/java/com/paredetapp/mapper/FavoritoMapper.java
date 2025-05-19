package com.paredetapp.mapper;

import com.paredetapp.dto.FavoritoDTO;
import com.paredetapp.model.Favorito;
import com.paredetapp.model.Producto;
import com.paredetapp.model.Usuario;

public class FavoritoMapper {

    public static FavoritoDTO toDTO(Favorito favorito) {
        FavoritoDTO dto = new FavoritoDTO();
        dto.setId(favorito.getId());
        dto.setUsuarioId(favorito.getUsuario().getId());
        dto.setProductoId(favorito.getProducto().getId());
        return dto;
    }

    public static Favorito toEntity(FavoritoDTO dto, Usuario usuario, Producto producto) {
        Favorito favorito = new Favorito();
        favorito.setUsuario(usuario);
        favorito.setProducto(producto);
        return favorito;
    }
}


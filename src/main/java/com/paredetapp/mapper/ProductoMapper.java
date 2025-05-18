package com.paredetapp.mapper;

import com.paredetapp.dto.ProductoDTO;
import com.paredetapp.model.Producto;
import com.paredetapp.model.Categoria;
import com.paredetapp.model.Coleccion;

public class ProductoMapper {

    public static ProductoDTO toDTO(Producto producto) {
        ProductoDTO dto = new ProductoDTO();
        dto.setId(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setDescripcion(producto.getDescripcion());
        dto.setPrecio(producto.getPrecio());
        dto.setPrecioRecomendado(producto.getPrecioRecomendado());
        dto.setStock(producto.getStock());
        dto.setImagenUrl(producto.getImagenUrl());
        dto.setReferencia(producto.getReferencia());
        dto.setFormato(producto.getFormato());
        dto.setTipoAplicacion(producto.getTipoAplicacion());
        dto.setPeso(producto.getPeso());
        dto.setFamilia(producto.getFamilia());
        dto.setCategoriaId(producto.getCategoria() != null ? producto.getCategoria().getId() : null);
        dto.setColeccionId(producto.getColeccion() != null ? producto.getColeccion().getId() : null);
        return dto;
    }

    public static Producto toEntity(ProductoDTO dto, Categoria categoria, Coleccion coleccion) {
        Producto producto = new Producto();
        producto.setId(dto.getId());
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setPrecioRecomendado(dto.getPrecioRecomendado());
        producto.setStock(dto.getStock());
        producto.setImagenUrl(dto.getImagenUrl());
        producto.setReferencia(dto.getReferencia());
        producto.setFormato(dto.getFormato());
        producto.setTipoAplicacion(dto.getTipoAplicacion());
        producto.setPeso(dto.getPeso());
        producto.setFamilia(dto.getFamilia());
        producto.setCategoria(categoria);
        producto.setColeccion(coleccion);
        return producto;
    }
}

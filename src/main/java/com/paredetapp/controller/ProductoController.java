package com.paredetapp.controller;

import com.paredetapp.dto.ProductoDTO;
import com.paredetapp.mapper.ProductoMapper;
import com.paredetapp.model.Categoria;
import com.paredetapp.model.Coleccion;
import com.paredetapp.model.Producto;
import com.paredetapp.service.CategoriaService;
import com.paredetapp.service.ColeccionService;
import com.paredetapp.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;
    private final CategoriaService categoriaService;
    private final ColeccionService coleccionService;

    @GetMapping
    public List<ProductoDTO> listarProductos() {
        return productoService.obtenerTodos()
                .stream()
                .map(ProductoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public Optional<ProductoDTO> obtenerProducto(@PathVariable UUID id) {
        return productoService.obtenerPorId(id)
                .map(ProductoMapper::toDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ProductoDTO crearProducto(@RequestBody ProductoDTO dto) {
        Categoria categoria = null;
        Coleccion coleccion = null;

        if (dto.getCategoriaId() != null) {
            categoria = categoriaService.obtenerPorId(dto.getCategoriaId());
        }

        if (dto.getColeccionId() != null) {
            coleccion = coleccionService.obtenerPorId(dto.getColeccionId());
        }

        Producto producto = ProductoMapper.toEntity(dto, categoria, coleccion);
        Producto guardado = productoService.guardarProducto(producto);
        return ProductoMapper.toDTO(guardado);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void eliminarProducto(@PathVariable UUID id) {
        productoService.eliminarProducto(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ProductoDTO actualizarProducto(@PathVariable UUID id, @RequestBody ProductoDTO dto) {
        Categoria categoria = null;
        Coleccion coleccion = null;

        if (dto.getCategoriaId() != null) {
            categoria = categoriaService.obtenerPorId(dto.getCategoriaId());
        }

        if (dto.getColeccionId() != null) {
            coleccion = coleccionService.obtenerPorId(dto.getColeccionId());
        }

        Producto producto = ProductoMapper.toEntity(dto, categoria, coleccion);
        producto.setId(id);
        Producto actualizado = productoService.guardarProducto(producto);
        return ProductoMapper.toDTO(actualizado);
    }


}



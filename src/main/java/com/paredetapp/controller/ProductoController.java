package com.paredetapp.controller;

import com.paredetapp.model.Producto;
import com.paredetapp.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    /**
     * Lista todos los productos (acceso completamente público).
     */
    @GetMapping
    public List<Producto> listarProductos() {
        return productoService.obtenerTodos();
    }

    /**
     * Obtiene un producto por ID (acceso completamente público).
     */
    @GetMapping("/{id}")
    public Optional<Producto> obtenerProducto(@PathVariable UUID id) {
        return productoService.obtenerPorId(id);
    }

    /**
     * Crea un nuevo producto (solo ADMIN).
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Producto crearProducto(@RequestBody Producto producto) {
        return productoService.guardarProducto(producto);
    }

    /**
     * Elimina un producto por ID (solo ADMIN).
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void eliminarProducto(@PathVariable UUID id) {
        productoService.eliminarProducto(id);
    }
}


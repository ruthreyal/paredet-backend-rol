package com.paredetapp.controller;

import com.paredetapp.model.Producto;
import com.paredetapp.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {
    @Autowired
    private ProductoService productoService;

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public List<Producto> listarProductos() {
        return productoService.obtenerTodos();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}")
    public Optional<Producto> obtenerProducto(@PathVariable UUID id) {
        return productoService.obtenerPorId(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Producto crearProducto(@RequestBody Producto producto) {
        return productoService.guardarProducto(producto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void eliminarProducto(@PathVariable UUID id) {
        productoService.eliminarProducto(id);
    }

}

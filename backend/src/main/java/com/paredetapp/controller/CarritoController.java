package com.paredetapp.controller;

import com.paredetapp.model.Carrito;
import com.paredetapp.service.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/carritos")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    // ✅ Solo ADMIN puede ver todos los carritos
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<Carrito> listarCarritos() {
        return carritoService.obtenerTodos();
    }

    // ✅ ADMIN o dueño del carrito pueden acceder
    @PreAuthorize("hasRole('ADMIN') or #id.toString() == authentication.principal.username")
    @GetMapping("/{id}")
    public Optional<Carrito> obtenerCarrito(@PathVariable UUID id) {
        return carritoService.obtenerPorId(id);
    }

    // ✅ Solo el propio usuario puede crear su carrito
    @PreAuthorize("hasRole('ADMIN') or #carrito.usuario.email == authentication.principal.username")
    @PostMapping
    public Carrito crearCarrito(@RequestBody Carrito carrito) {
        return carritoService.guardarCarrito(carrito);
    }

    // ✅ Solo ADMIN puede eliminar carritos
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void eliminarCarrito(@PathVariable UUID id) {
        carritoService.eliminarCarrito(id);
    }
}



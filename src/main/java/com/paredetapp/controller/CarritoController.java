package com.paredetapp.controller;

import com.paredetapp.model.Carrito;
import com.paredetapp.service.CarritoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/carritos")
@RequiredArgsConstructor
public class CarritoController {

    private final CarritoService carritoService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<Carrito> listarCarritos() {
        return carritoService.obtenerTodos();
    }

    @PreAuthorize("hasRole('ADMIN') or @carritoService.esPropietario(#id, #principal.username)")
    @GetMapping("/{id}")
    public Optional<Carrito> obtenerCarrito(@PathVariable UUID id, @AuthenticationPrincipal UserDetails principal) {
        return carritoService.obtenerPorId(id);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public Carrito crearCarrito(@RequestBody Carrito carrito, @AuthenticationPrincipal UserDetails principal) {
        if (!carritoService.emailCoincide(carrito.getUsuarioId(), principal.getUsername())) {
            throw new SecurityException("No autorizado para crear carrito para otro usuario");
        }
        return carritoService.guardarCarrito(carrito);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void eliminarCarrito(@PathVariable UUID id) {
        carritoService.eliminarCarrito(id);
    }
}






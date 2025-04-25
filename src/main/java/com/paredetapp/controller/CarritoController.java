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

    /**
     * Solo ADMIN puede ver todos los carritos.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<Carrito> listarCarritos() {
        return carritoService.obtenerTodos();
    }

    /**
     * ADMIN o el dueño del carrito puede acceder a su propio carrito.
     */
    @PreAuthorize("hasRole('ADMIN') or @carritoService.esPropietario(#id, #principal.username)")
    @GetMapping("/{id}")
    public Optional<Carrito> obtenerCarrito(@PathVariable UUID id, @AuthenticationPrincipal UserDetails principal) {
        return carritoService.obtenerPorId(id);
    }

    /**
     * Solo el propio usuario autenticado puede crear su carrito.
     */
    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public Carrito crearCarrito(@RequestBody Carrito carrito, @AuthenticationPrincipal UserDetails principal) {
        // Validar que el UUID del usuario en el token coincide con el del carrito
        if (!carrito.getUsuarioId().toString().equals(principal.getUsername())) {
            throw new SecurityException("No autorizado para crear carrito para otro usuario");
        }
        return carritoService.guardarCarrito(carrito);
    }

    /**
     * Solo ADMIN puede eliminar cualquier carrito.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void eliminarCarrito(@PathVariable UUID id) {
        carritoService.eliminarCarrito(id);
    }
}





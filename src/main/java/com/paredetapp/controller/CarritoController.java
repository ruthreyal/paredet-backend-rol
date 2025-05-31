package com.paredetapp.controller;

import com.paredetapp.dto.AddToCartRequest;
import com.paredetapp.model.Carrito;
import com.paredetapp.model.Usuario;
import com.paredetapp.service.CarritoService;
import com.paredetapp.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/carrito")
@RequiredArgsConstructor
public class CarritoController {

    private final CarritoService carritoService;
    @Autowired
    private UsuarioService usuarioService;

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

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarProductoDelCarrito(
            @PathVariable UUID id,
            @AuthenticationPrincipal UserDetails userDetails) {

        Usuario usuario = usuarioService.obtenerPorEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        carritoService.eliminarCarritoPorUsuario(id, usuario.getId());

        return ResponseEntity.ok("Producto eliminado del carrito");
    }


    @PostMapping("/add")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> agregarAlCarrito(
            @RequestBody AddToCartRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {

        Usuario usuario = usuarioService.obtenerPorEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        carritoService.agregarAlCarrito(usuario.getId(), request.getProductoId(), request.getCantidad());

        return ResponseEntity.ok("Producto añadido al carrito");
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/usuario")
    public List<Carrito> obtenerCarritosUsuario(@AuthenticationPrincipal UserDetails userDetails) {
        Usuario usuario = usuarioService.obtenerPorEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return carritoService.obtenerPorUsuario(usuario.getId());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> actualizarCantidad(
            @PathVariable UUID id,
            @RequestBody Carrito carritoActualizado,
            @AuthenticationPrincipal UserDetails userDetails) {

        Usuario usuario = usuarioService.obtenerPorEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        carritoService.actualizarCantidad(id, carritoActualizado.getCantidad(), usuario.getId());

        return ResponseEntity.ok("Cantidad actualizada");
    }

}






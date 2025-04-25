package com.paredetapp.controller;

import com.paredetapp.model.Favorito;
import com.paredetapp.service.FavoritoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/favoritos")
@RequiredArgsConstructor
public class FavoritoController {

    private final FavoritoService favoritoService;

    /**
     * Obtener los favoritos de un usuario.
     */
    @PreAuthorize("#usuarioId.toString() == authentication.principal.username or hasRole('ADMIN')")
    @GetMapping("/{usuarioId}")
    public List<Favorito> listarFavoritos(@PathVariable UUID usuarioId) {
        return favoritoService.obtenerPorUsuario(usuarioId);
    }

    /**
     * Añadir un producto a favoritos.
     */
    @PreAuthorize("#usuarioId.toString() == authentication.principal.username")
    @PostMapping("/{usuarioId}/{productoId}")
    public Favorito agregarFavorito(@PathVariable UUID usuarioId, @PathVariable UUID productoId) {
        return favoritoService.agregarFavorito(usuarioId, productoId);
    }

    /**
     * Eliminar un producto de favoritos.
     */
    @PreAuthorize("#usuarioId.toString() == authentication.principal.username or hasRole('ADMIN')")
    @DeleteMapping("/{usuarioId}/{productoId}")
    public void eliminarFavorito(@PathVariable UUID usuarioId, @PathVariable UUID productoId) {
        favoritoService.eliminarFavorito(usuarioId, productoId);
    }
}


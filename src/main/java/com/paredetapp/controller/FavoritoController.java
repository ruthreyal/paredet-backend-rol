package com.paredetapp.controller;

import com.paredetapp.model.Favorito;
import com.paredetapp.service.FavoritoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/favoritos")
@RequiredArgsConstructor
public class FavoritoController {

    private final FavoritoService favoritoService;

    @PreAuthorize("hasRole('ADMIN') or @favoritoService.emailCoincide(#usuarioId, #principal.username)")
    @GetMapping("/{usuarioId}")
    public List<Favorito> listarFavoritos(@PathVariable UUID usuarioId, @AuthenticationPrincipal UserDetails principal) {
        return favoritoService.obtenerPorUsuario(usuarioId);
    }

    @PreAuthorize("@favoritoService.emailCoincide(#usuarioId, #principal.username)")
    @PostMapping("/{usuarioId}/{productoId}")
    public Favorito agregarFavorito(@PathVariable UUID usuarioId, @PathVariable UUID productoId,
                                    @AuthenticationPrincipal UserDetails principal) {
        return favoritoService.agregarFavorito(usuarioId, productoId);
    }

    @PreAuthorize("hasRole('ADMIN') or @favoritoService.emailCoincide(#usuarioId, #principal.username)")
    @DeleteMapping("/{usuarioId}/{productoId}")
    public void eliminarFavorito(@PathVariable UUID usuarioId, @PathVariable UUID productoId,
                                 @AuthenticationPrincipal UserDetails principal) {
        favoritoService.eliminarFavorito(usuarioId, productoId);
    }
}



package com.paredetapp.controller;

import com.paredetapp.dto.FavoritoDTO;
import com.paredetapp.mapper.FavoritoMapper;
import com.paredetapp.model.Favorito;
import com.paredetapp.model.Producto;
import com.paredetapp.model.Usuario;
import com.paredetapp.service.FavoritoService;
import com.paredetapp.service.ProductoService;
import com.paredetapp.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/favoritos")
@RequiredArgsConstructor
public class FavoritoController {

    private final FavoritoService favoritoService;
    private final UsuarioService usuarioService;
    private final ProductoService productoService;

    @GetMapping("/{usuarioId}")
    public List<FavoritoDTO> listarFavoritos(@PathVariable UUID usuarioId) {
        return favoritoService.obtenerFavoritosDeUsuario(usuarioId)
                .stream()
                .map(FavoritoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @PostMapping
    public FavoritoDTO agregarFavorito(@RequestBody FavoritoDTO dto) {
        Usuario usuario = usuarioService.obtenerPorId(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Producto producto = productoService.obtenerPorId(dto.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        Favorito favorito = FavoritoMapper.toEntity(dto, usuario, producto);
        return FavoritoMapper.toDTO(favoritoService.guardar(favorito));
    }


    @DeleteMapping
    public void eliminarFavorito(@RequestParam UUID usuarioId, @RequestParam UUID productoId) {
        favoritoService.eliminar(usuarioId, productoId);
    }
}

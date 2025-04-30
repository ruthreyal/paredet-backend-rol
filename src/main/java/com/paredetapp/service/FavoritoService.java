package com.paredetapp.service;

import com.paredetapp.model.Favorito;
import com.paredetapp.model.Producto;
import com.paredetapp.model.Usuario;
import com.paredetapp.repository.FavoritoRepository;
import com.paredetapp.repository.ProductoRepository;
import com.paredetapp.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FavoritoService {

    private final FavoritoRepository favoritoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;

    public List<Favorito> obtenerPorUsuario(UUID usuarioId) {
        return favoritoRepository.findByUsuarioId(usuarioId);
    }

    public Favorito agregarFavorito(UUID usuarioId, UUID productoId) {
        if (favoritoRepository.existsByUsuarioIdAndProductoId(usuarioId, productoId)) {
            throw new IllegalStateException("Ya está en favoritos");
        }

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        Favorito favorito = new Favorito();
        favorito.setUsuario(usuario);
        favorito.setProducto(producto);

        return favoritoRepository.save(favorito);
    }

    public void eliminarFavorito(UUID usuarioId, UUID productoId) {
        favoritoRepository.deleteByUsuarioIdAndProductoId(usuarioId, productoId);
    }

    // Verifica si el email autenticado corresponde al usuario del recurso
    public boolean emailCoincide(UUID usuarioId, String email) {
        return usuarioRepository.findById(usuarioId)
                .map(u -> u.getEmail().equalsIgnoreCase(email))
                .orElse(false);
    }
}



package com.paredetapp.service;

import com.paredetapp.model.Favorito;
import com.paredetapp.repository.FavoritoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FavoritoService {

    private final FavoritoRepository favoritoRepository;

    public List<Favorito> obtenerFavoritosDeUsuario(UUID usuarioId) {
        return favoritoRepository.findByUsuarioId(usuarioId);
    }

    public boolean esFavorito(UUID usuarioId, UUID productoId) {
        return favoritoRepository.existsByUsuarioIdAndProductoId(usuarioId, productoId);
    }

    public Favorito guardar(Favorito favorito) {
        return favoritoRepository.save(favorito);
    }

    @Transactional
    public void eliminar(UUID usuarioId, UUID productoId) {
        favoritoRepository.deleteByUsuarioIdAndProductoId(usuarioId, productoId);
    }
}


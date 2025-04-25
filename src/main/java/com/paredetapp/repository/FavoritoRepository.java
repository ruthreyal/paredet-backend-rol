package com.paredetapp.repository;

import com.paredetapp.model.Favorito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FavoritoRepository extends JpaRepository<Favorito, UUID> {

    List<Favorito> findByUsuarioId(UUID usuarioId);

    boolean existsByUsuarioIdAndProductoId(UUID usuarioId, UUID productoId);

    void deleteByUsuarioIdAndProductoId(UUID usuarioId, UUID productoId);

    Optional<Favorito> findByUsuarioIdAndProductoId(UUID usuarioId, UUID productoId);
}


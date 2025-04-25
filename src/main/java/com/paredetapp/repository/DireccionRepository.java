package com.paredetapp.repository;

import com.paredetapp.model.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DireccionRepository extends JpaRepository<Direccion, UUID> {
    List<Direccion> findByUsuarioId(UUID usuarioId);
}


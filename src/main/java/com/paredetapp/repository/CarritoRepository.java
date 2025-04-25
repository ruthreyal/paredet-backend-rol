package com.paredetapp.repository;

import com.paredetapp.model.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CarritoRepository extends JpaRepository<Carrito, UUID> {
    List<Carrito> findByUsuario_Id(UUID usuarioId); // ✅ ya no fallará
}


package com.paredetapp.repository;

import com.paredetapp.model.ImagenProducto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ImagenProductoRepository extends JpaRepository<ImagenProducto, UUID> {
    List<ImagenProducto> findByProductoId(UUID productoId);
}

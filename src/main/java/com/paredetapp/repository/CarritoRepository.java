package com.paredetapp.repository;

import com.paredetapp.model.Carrito;
import com.paredetapp.model.Producto;
import com.paredetapp.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CarritoRepository extends JpaRepository<Carrito, UUID> {
    List<Carrito> findByUsuario_Id(UUID usuarioId);
    Optional<Carrito> findByUsuarioAndProducto(Usuario usuario, Producto producto);

    // Opcionales para funcionalidad completa:
    void deleteByUsuarioAndProducto(Usuario usuario, Producto producto);
    void deleteByUsuario(Usuario usuario);
}



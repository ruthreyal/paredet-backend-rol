package com.paredetapp.repository;

import com.paredetapp.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    // Buscar usuario por email (clave para login y autenticación)
    Optional<Usuario> findByEmail(String email);
}



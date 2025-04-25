package com.paredetapp.repository;

import com.paredetapp.model.Coleccion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ColeccionRepository extends JpaRepository<Coleccion, UUID> {
}


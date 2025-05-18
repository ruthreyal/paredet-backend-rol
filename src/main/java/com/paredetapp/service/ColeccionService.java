package com.paredetapp.service;

import com.paredetapp.model.Coleccion;
import com.paredetapp.repository.ColeccionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ColeccionService {

    private final ColeccionRepository coleccionRepository;

    public List<Coleccion> obtenerTodas() {
        return coleccionRepository.findAll();
    }

    public Coleccion guardar(Coleccion coleccion) {
        return coleccionRepository.save(coleccion);
    }

    public void eliminarPorId(UUID id) {
        coleccionRepository.deleteById(id);
    }

    public Coleccion obtenerPorId(UUID id) {
        return coleccionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Colección no encontrada"));
    }
}



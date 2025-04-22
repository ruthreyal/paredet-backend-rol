package com.paredetapp.service;

import com.paredetapp.model.Coleccion;
import com.paredetapp.repository.ColeccionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ColeccionService {

    private final ColeccionRepository coleccionRepository;

    public List<Coleccion> obtenerTodas() {
        return coleccionRepository.findAll();
    }

    public Optional<Coleccion> obtenerPorId(UUID id) {
        return coleccionRepository.findById(id);
    }

    public Coleccion guardar(Coleccion coleccion) {
        return coleccionRepository.save(coleccion);
    }

    public void eliminar(UUID id) {
        coleccionRepository.deleteById(id);
    }
}



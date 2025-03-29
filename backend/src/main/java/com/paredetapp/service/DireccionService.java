package com.paredetapp.service;

import com.paredetapp.model.Direccion;
import com.paredetapp.repository.DireccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DireccionService {

    @Autowired
    private DireccionRepository direccionRepository;

    public List<Direccion> obtenerTodas() {
        return direccionRepository.findAll();
    }

    public Optional<Direccion> obtenerPorId(UUID id) {
        return direccionRepository.findById(id);
    }

    public Direccion guardarDireccion(Direccion direccion) {
        return direccionRepository.save(direccion);
    }

    public void eliminarDireccion(UUID id) {
        direccionRepository.deleteById(id);
    }
}


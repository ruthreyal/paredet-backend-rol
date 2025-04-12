package com.paredetapp.service;

import com.paredetapp.model.Direccion;
import com.paredetapp.repository.DireccionRepository;
import com.paredetapp.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DireccionService {

    @Autowired
    private UsuarioRepository usuarioRepository;


    @Autowired
    private DireccionRepository direccionRepository;

    public boolean esPropietario(UUID direccionId, String emailUsuario) {
        return direccionRepository.findById(direccionId)
                .map(d -> d.getUsuario().getEmail().equals(emailUsuario))
                .orElse(false);
    }



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


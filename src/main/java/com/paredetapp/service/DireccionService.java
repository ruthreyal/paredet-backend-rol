package com.paredetapp.service;

import com.paredetapp.model.Direccion;
import com.paredetapp.repository.DireccionRepository;
import com.paredetapp.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DireccionService {

    private final DireccionRepository direccionRepository;
    private final UsuarioRepository usuarioRepository;

    public boolean esPropietario(UUID direccionId, String email) {
        return direccionRepository.findById(direccionId)
                .map(d -> d.getUsuario().getEmail().equalsIgnoreCase(email))
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




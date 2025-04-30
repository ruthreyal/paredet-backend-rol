package com.paredetapp.service;

import com.paredetapp.model.Carrito;
import com.paredetapp.repository.CarritoRepository;
import com.paredetapp.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CarritoService {

    private final CarritoRepository carritoRepository;
    private final UsuarioRepository usuarioRepository;

    public List<Carrito> obtenerTodos() {
        return carritoRepository.findAll();
    }

    public Optional<Carrito> obtenerPorId(UUID id) {
        return carritoRepository.findById(id);
    }

    public Carrito guardarCarrito(Carrito carrito) {
        return carritoRepository.save(carrito);
    }

    public void eliminarCarrito(UUID id) {
        carritoRepository.deleteById(id);
    }

    public boolean esPropietario(UUID carritoId, String email) {
        return carritoRepository.findById(carritoId)
                .map(c -> usuarioRepository.findById(c.getUsuarioId())
                        .map(u -> u.getEmail().equalsIgnoreCase(email))
                        .orElse(false))
                .orElse(false);
    }

    public boolean emailCoincide(UUID usuarioId, String email) {
        return usuarioRepository.findById(usuarioId)
                .map(u -> u.getEmail().equalsIgnoreCase(email))
                .orElse(false);
    }
}



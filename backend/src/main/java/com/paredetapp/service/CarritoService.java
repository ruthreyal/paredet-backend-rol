package com.paredetapp.service;

import com.paredetapp.model.Carrito;
import com.paredetapp.repository.CarritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;

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
}


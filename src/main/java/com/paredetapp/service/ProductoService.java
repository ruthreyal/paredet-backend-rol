package com.paredetapp.service;

import com.paredetapp.model.Producto;
import com.paredetapp.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;

    public List<Producto> obtenerTodos() {
        return productoRepository.findAll();
    }

    public Optional<Producto> obtenerPorId(UUID id) {
        return productoRepository.findById(id);
    }

    public Producto guardarProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    public void eliminarProducto(UUID id) {
        productoRepository.deleteById(id);
    }

    public boolean nombreExiste(String nombre) {
        return productoRepository.existsByNombre(nombre);
    }

    public boolean referenciaExiste(String referencia) {
        return productoRepository.existsByReferencia(referencia);
    }

}


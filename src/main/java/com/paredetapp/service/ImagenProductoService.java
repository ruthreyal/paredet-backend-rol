package com.paredetapp.service;

import com.paredetapp.model.ImagenProducto;
import com.paredetapp.repository.ImagenProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImagenProductoService {

    private final ImagenProductoRepository imagenRepo;

    public List<ImagenProducto> obtenerPorProductoId(UUID productoId) {
        return imagenRepo.findByProductoId(productoId);
    }

    public ImagenProducto guardar(ImagenProducto imagen) {
        return imagenRepo.save(imagen);
    }

    public void eliminar(UUID id) {
        imagenRepo.deleteById(id);
    }
}


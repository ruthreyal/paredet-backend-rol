package com.paredetapp.controller;

import com.paredetapp.dto.ImagenProductoDTO;
import com.paredetapp.mapper.ImagenProductoMapper;
import com.paredetapp.model.ImagenProducto;
import com.paredetapp.model.Producto;
import com.paredetapp.service.ImagenProductoService;
import com.paredetapp.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/imagenes-producto")
@RequiredArgsConstructor
public class ImagenProductoController {

    private final ImagenProductoService imagenService;
    private final ProductoService productoService;

    @GetMapping("/producto/{productoId}")
    public List<ImagenProductoDTO> obtenerPorProducto(@PathVariable UUID productoId) {
        return imagenService.obtenerPorProductoId(productoId)
                .stream()
                .map(ImagenProductoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ImagenProductoDTO crearImagen(@RequestBody ImagenProductoDTO dto) {
        Producto producto = productoService.obtenerPorId(dto.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        ImagenProducto imagen = ImagenProductoMapper.toEntity(dto, producto);
        return ImagenProductoMapper.toDTO(imagenService.guardar(imagen));
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable UUID id) {
        imagenService.eliminar(id);
    }
}

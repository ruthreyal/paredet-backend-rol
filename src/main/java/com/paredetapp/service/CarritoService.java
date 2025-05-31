package com.paredetapp.service;

import com.paredetapp.model.Carrito;
import com.paredetapp.model.Producto;
import com.paredetapp.model.Usuario;
import com.paredetapp.repository.CarritoRepository;
import com.paredetapp.repository.ProductoRepository;
import com.paredetapp.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CarritoService {

    private final CarritoRepository carritoRepository;
    private final UsuarioRepository usuarioRepository;
    @Autowired
    private ProductoRepository productoRepository;

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

    public void agregarAlCarrito(UUID usuarioId, UUID productoId, int cantidad) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        // Buscar si ya existe ese producto en el carrito del usuario
        Optional<Carrito> existente = carritoRepository.findByUsuarioAndProducto(usuario, producto);

        if (existente.isPresent()) {
            Carrito carrito = existente.get();
            carrito.setCantidad(carrito.getCantidad() + cantidad); // sumar cantidad
            carritoRepository.save(carrito);
        } else {
            Carrito nuevo = new Carrito();
            nuevo.setUsuario(usuario);
            nuevo.setProducto(producto);
            nuevo.setCantidad(cantidad);
            carritoRepository.save(nuevo);
        }
    }

    public List<Carrito> obtenerPorUsuario(UUID usuarioId) {
        return carritoRepository.findByUsuario_Id(usuarioId);
    }

    public void actualizarCantidad(UUID carritoId, int nuevaCantidad, UUID usuarioId) {
        Carrito carrito = carritoRepository.findById(carritoId)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        if (!carrito.getUsuarioId().equals(usuarioId)) {
            throw new SecurityException("No autorizado");
        }

        carrito.setCantidad(nuevaCantidad);
        carritoRepository.save(carrito);
    }

    public void eliminarCarritoPorUsuario(UUID carritoId, UUID usuarioId) {
        Carrito carrito = carritoRepository.findById(carritoId)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        if (!carrito.getUsuarioId().equals(usuarioId)) {
            throw new SecurityException("No autorizado para eliminar este carrito");
        }

        carritoRepository.deleteById(carritoId);
    }


}



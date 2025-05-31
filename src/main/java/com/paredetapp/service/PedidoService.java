package com.paredetapp.service;

import com.paredetapp.model.Pedido;
import com.paredetapp.model.Usuario;
import com.paredetapp.repository.PedidoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.paredetapp.model.Carrito;
import com.paredetapp.repository.CarritoRepository;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final CarritoRepository carritoRepository;


    public List<Pedido> obtenerTodos() {
        return pedidoRepository.findAll();
    }

    public Optional<Pedido> obtenerPorId(UUID id) {
        return pedidoRepository.findById(id);
    }

    public Pedido guardarPedido(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    public boolean esPropietario(UUID pedidoId, String emailUsuario) {
        return pedidoRepository.findById(pedidoId)
                .map(p -> p.getUsuario().getEmail().equalsIgnoreCase(emailUsuario))
                .orElse(false);
    }


    public void eliminarPedido(UUID id) {
        pedidoRepository.deleteById(id);
    }

    @Transactional
    public void realizarPedidoDesdeCarrito(Usuario usuario) {
        // Obtener productos del carrito
        List<Carrito> carritoItems = carritoRepository.findByUsuario_Id(usuario.getId());

        if (carritoItems.isEmpty()) {
            throw new RuntimeException("El carrito está vacío");
        }

        // Crear nuevo pedido
        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setFechaCreacion(LocalDateTime.now());
        pedido.setEstado("PENDIENTE");

        double total = carritoItems.stream()
                .mapToDouble(item -> item.getProducto().getPrecio() * item.getCantidad())
                .sum();
        pedido.setTotal(total);

        pedidoRepository.save(pedido);

        // Vaciar carrito
        carritoRepository.deleteByUsuario(usuario);
    }

    public List<Pedido> obtenerPorUsuarioId(UUID usuarioId) {
        return pedidoRepository.findByUsuario_Id(usuarioId);
    }

}

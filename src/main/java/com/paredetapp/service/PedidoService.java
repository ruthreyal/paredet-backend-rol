package com.paredetapp.service;

import com.paredetapp.model.Pedido;
import com.paredetapp.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;

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
}

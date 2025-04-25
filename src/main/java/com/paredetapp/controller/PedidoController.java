package com.paredetapp.controller;

import com.paredetapp.model.Pedido;
import com.paredetapp.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    /**
     * Solo ADMIN puede ver todos los pedidos.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<Pedido> listarPedidos() {
        return pedidoService.obtenerTodos();
    }

    /**
     * ADMIN o el propietario del pedido pueden ver un pedido específico.
     */
    @PreAuthorize("hasRole('ADMIN') or @pedidoService.esPropietario(#id, authentication.principal.username)")
    @GetMapping("/{id}")
    public Optional<Pedido> obtenerPedido(@PathVariable UUID id) {
        return pedidoService.obtenerPorId(id);
    }

    /**
     * Cualquier usuario autenticado puede crear un pedido.
     */
    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public Pedido crearPedido(@RequestBody Pedido pedido) {
        return pedidoService.guardarPedido(pedido);
    }

    /**
     * ADMIN o el propietario pueden eliminar el pedido.
     */
    @PreAuthorize("hasRole('ADMIN') or @pedidoService.esPropietario(#id, authentication.principal.username)")
    @DeleteMapping("/{id}")
    public void eliminarPedido(@PathVariable UUID id) {
        pedidoService.eliminarPedido(id);
    }
}



package com.paredetapp.controller;

import com.paredetapp.model.Pedido;
import com.paredetapp.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    // 🔐 ADMIN ve todos, USER no puede ver todos
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<Pedido> listarPedidos() {
        return pedidoService.obtenerTodos();
    }

    // 🔐 ADMIN o dueño del pedido
    @PreAuthorize("hasRole('ADMIN') or @pedidoService.esPropietario(#id, authentication.principal.username)")
    @GetMapping("/{id}")
    public Optional<Pedido> obtenerPedido(@PathVariable UUID id) {
        return pedidoService.obtenerPorId(id);
    }

    // 🔓 Cualquier usuario autenticado puede crear pedido
    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public Pedido crearPedido(@RequestBody Pedido pedido) {
        return pedidoService.guardarPedido(pedido);
    }

    // 🔐 ADMIN o dueño del pedido
    @PreAuthorize("hasRole('ADMIN') or @pedidoService.esPropietario(#id, authentication.principal.username)")
    @DeleteMapping("/{id}")
    public void eliminarPedido(@PathVariable UUID id) {
        pedidoService.eliminarPedido(id);
    }
}


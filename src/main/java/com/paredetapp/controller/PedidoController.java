package com.paredetapp.controller;

import com.paredetapp.model.Pedido;
import com.paredetapp.model.Usuario;
import com.paredetapp.service.CarritoService;
import com.paredetapp.service.PedidoService;
import com.paredetapp.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private CarritoService carritoService;

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
     * ADMIN o propietario pueden eliminar su pedido.
     */
    @PreAuthorize("hasRole('ADMIN') or @pedidoService.esPropietario(#id, authentication.principal.username)")
    @DeleteMapping("/{id}")
    public void eliminarPedido(@PathVariable UUID id) {
        pedidoService.eliminarPedido(id);
    }

    /**
     * Cualquier usuario autenticado puede guardar un pedido.
     */
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping
    public Pedido guardarPedido(@RequestBody Pedido pedido) {
        return pedidoService.guardarPedido(pedido);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/finalizar")
    public ResponseEntity<String> realizarPedido(@AuthenticationPrincipal UserDetails userDetails) {
        Usuario usuario = usuarioService.obtenerPorEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        pedidoService.realizarPedidoDesdeCarrito(usuario);

        return ResponseEntity.ok("Pedido realizado con éxito");
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/realizar")
    public ResponseEntity<String> realizarPedidoDesdeCarrito(@AuthenticationPrincipal UserDetails userDetails) {
        Usuario usuario = usuarioService.obtenerPorEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        pedidoService.realizarPedidoDesdeCarrito(usuario);
        return ResponseEntity.ok("Pedido realizado correctamente");
    }


}



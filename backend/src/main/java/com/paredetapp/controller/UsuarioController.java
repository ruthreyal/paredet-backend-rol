package com.paredetapp.controller;

import com.paredetapp.model.Usuario;
import com.paredetapp.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Solo el ADMIN puede ver la lista completa de usuarios.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioService.obtenerTodos();
    }

    /**
     * Permite al ADMIN o al propio usuario acceder a sus datos.
     */
    @PreAuthorize("hasRole('ADMIN') or #id.toString() == authentication.principal.username")
    @GetMapping("/{id}")
    public Optional<Usuario> obtenerUsuario(@PathVariable UUID id) {
        return usuarioService.obtenerPorId(id);
    }

    /**
     * Permite al ADMIN o al propio usuario acceder a sus datos por email.
     */
    @PreAuthorize("hasRole('ADMIN') or #email == authentication.principal.username")
    @GetMapping("/email/{email}")
    public Optional<Usuario> obtenerUsuarioPorEmail(@PathVariable String email) {
        return usuarioService.obtenerPorEmail(email);
    }

    /**
     * Solo el ADMIN puede crear nuevos usuarios con rol personalizado.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Usuario crearUsuario(@RequestBody Usuario usuario) {
        return usuarioService.guardarUsuario(usuario);
    }

    /**
     * Solo el ADMIN o el propio usuario pueden actualizar sus datos.
     */
    @PreAuthorize("hasRole('ADMIN') or #id.toString() == authentication.principal.username")
    @PutMapping("/{id}")
    public Usuario actualizarUsuario(@PathVariable UUID id, @RequestBody Usuario usuarioActualizado) {
        return usuarioService.actualizarUsuario(id, usuarioActualizado);
    }

    /**
     * Solo el ADMIN puede eliminar usuarios.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void eliminarUsuario(@PathVariable UUID id) {
        usuarioService.eliminarUsuario(id);
    }
}
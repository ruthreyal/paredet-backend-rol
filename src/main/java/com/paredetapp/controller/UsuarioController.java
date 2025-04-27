package com.paredetapp.controller;

import com.paredetapp.model.Usuario;
import com.paredetapp.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<Usuario> listarUsuarios() {
        return usuarioService.obtenerTodos();
    }

    /**
     * Permite al ADMIN o al propio usuario acceder a sus datos.
     */
    @GetMapping("/{id}")
    public Optional<Usuario> obtenerUsuario(@PathVariable UUID id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Si es ADMIN, puede acceder a cualquier usuario
        if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return usuarioService.obtenerPorId(id);
        }

        // Si es USER, solo puede acceder a su propio ID
        if (!authentication.getName().equals(id.toString())) {
            throw new AccessDeniedException("No tienes permiso para acceder a este recurso");
        }

        return usuarioService.obtenerPorId(id);
    }

    /**
     * Permite al ADMIN o al propio usuario acceder a sus datos por email.
     */
    @GetMapping("/email/{email}")
    @PreAuthorize("hasRole('ADMIN') or #email == authentication.name")
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
    @PutMapping("/{id}")
    public Usuario actualizarUsuario(@PathVariable UUID id, @RequestBody Usuario usuarioActualizado) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return usuarioService.actualizarUsuario(id, usuarioActualizado);
        }

        if (!authentication.getName().equals(id.toString())) {
            throw new AccessDeniedException("No tienes permiso para actualizar este recurso");
        }

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


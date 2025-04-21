package com.paredetapp.controller;

import com.paredetapp.model.Usuario;
import com.paredetapp.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // ✅ Solo ADMIN puede listar todos los usuarios
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioService.obtenerTodos();
    }

    // ✅ ADMIN o el propio usuario pueden acceder a sus datos
    @PreAuthorize("hasRole('ADMIN') or #id.toString() == authentication.principal.username")
    @GetMapping("/{id}")
    public Optional<Usuario> obtenerUsuario(@PathVariable UUID id) {
        return usuarioService.obtenerPorId(id);
    }

    // ✅ ADMIN o el propio usuario pueden acceder por email
    @PreAuthorize("hasRole('ADMIN') or #email == authentication.principal.username")
    @GetMapping("/email/{email}")
    public Optional<Usuario> obtenerUsuarioPorEmail(@PathVariable String email) {
        return usuarioService.obtenerPorEmail(email);
    }

    // ✅ Solo ADMIN puede crear usuarios
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Usuario crearUsuario(@RequestBody Usuario usuario) {
        return usuarioService.guardarUsuario(usuario);
    }

    // ✅ Solo ADMIN puede eliminar usuarios
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void eliminarUsuario(@PathVariable UUID id) {
        usuarioService.eliminarUsuario(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id.toString() == authentication.principal.username")
    public Usuario actualizarUsuario(@PathVariable UUID id, @RequestBody Usuario usuarioActualizado) {
        Optional<Usuario> usuarioOptional = usuarioService.obtenerPorId(id);
        if (usuarioOptional.isPresent()) {
            Usuario usuarioExistente = usuarioOptional.get();
            usuarioExistente.setNombre(usuarioActualizado.getNombre());
            usuarioExistente.setApellido(usuarioActualizado.getApellido());
            usuarioExistente.setTelefono(usuarioActualizado.getTelefono());
            usuarioExistente.setDireccion(usuarioActualizado.getDireccion());
            usuarioExistente.setCiudad(usuarioActualizado.getCiudad());
            usuarioExistente.setPais(usuarioActualizado.getPais());
            usuarioExistente.setCodigoPostal(usuarioActualizado.getCodigoPostal());
            return usuarioService.guardarUsuario(usuarioExistente);
        } else {
            throw new RuntimeException("Usuario no encontrado");
        }
    }

}



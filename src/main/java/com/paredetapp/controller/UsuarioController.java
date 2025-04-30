package com.paredetapp.controller;

import com.paredetapp.dto.UsuarioDTO;
import com.paredetapp.model.Usuario;
import com.paredetapp.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    private UsuarioDTO convertirADTO(Usuario usuario) {
        return new UsuarioDTO(
            usuario.getId(),
            usuario.getNombre(),
            usuario.getApellido(),
            usuario.getEmail(),
            usuario.getTelefono(),
            usuario.getDireccion(),
            usuario.getCiudad(),
            usuario.getCodigoPostal(),
            usuario.getPais(),
            usuario.getRol() != null ? usuario.getRol().getNombre() : null
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<UsuarioDTO> listarUsuarios() {
        return usuarioService.obtenerTodos()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ADMIN') or #id.toString() == authentication.principal.username")
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> obtenerUsuario(@PathVariable UUID id) {
        Optional<Usuario> usuarioOpt = usuarioService.obtenerPorId(id);
        return usuarioOpt.map(usuario -> ResponseEntity.ok(convertirADTO(usuario)))
                         .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN') or #email == authentication.principal.username")
    @GetMapping("/email/{email}")
    public ResponseEntity<UsuarioDTO> obtenerUsuarioPorEmail(@PathVariable String email) {
        Optional<Usuario> usuarioOpt = usuarioService.obtenerPorEmail(email);
        return usuarioOpt.map(usuario -> ResponseEntity.ok(convertirADTO(usuario)))
                         .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<UsuarioDTO> crearUsuario(@RequestBody Usuario usuario) {
        Usuario creado = usuarioService.guardarUsuario(usuario);
        return ResponseEntity.ok(convertirADTO(creado));
    }

    @PreAuthorize("hasRole('ADMIN') or #id.toString() == authentication.principal.username")
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> actualizarUsuario(@PathVariable UUID id, @RequestBody Usuario usuarioActualizado) {
        Usuario actualizado = usuarioService.actualizarUsuario(id, usuarioActualizado);
        return ResponseEntity.ok(convertirADTO(actualizado));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void eliminarUsuario(@PathVariable UUID id) {
        usuarioService.eliminarUsuario(id);
    }
}

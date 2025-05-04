package com.paredetapp.controller;

import com.paredetapp.dto.CambiarPasswordRequest;
import com.paredetapp.dto.UsuarioAdminDTO;
import com.paredetapp.dto.UsuarioDTO;
import com.paredetapp.model.Usuario;
import com.paredetapp.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
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

    @PreAuthorize("hasRole('ADMIN') or @usuarioService.esPropietario(#id, authentication.principal.username)")
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> obtenerUsuario(@PathVariable UUID id) {
        Optional<Usuario> usuarioOpt = usuarioService.obtenerPorId(id);
        return usuarioOpt.map(usuario -> ResponseEntity.ok(convertirADTO(usuario)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN') or #email == authentication.principal.username")
    @GetMapping("/email/{email}")
    public ResponseEntity<UsuarioDTO> obtenerUsuarioPorEmail(@PathVariable String email) {
        String decodedEmail = URLDecoder.decode(email, StandardCharsets.UTF_8);
        Optional<Usuario> usuarioOpt = usuarioService.obtenerPorEmail(decodedEmail);
        return usuarioOpt.map(usuario -> ResponseEntity.ok(convertirADTO(usuario)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<UsuarioDTO> crearUsuario(@Valid @RequestBody Usuario usuario) {
        Usuario creado = usuarioService.guardarUsuario(usuario);
        return ResponseEntity.ok(convertirADTO(creado));
    }

    @PreAuthorize("hasRole('ADMIN') or #email == authentication.principal.username")
    @PutMapping("/email/{email}")
    public ResponseEntity<UsuarioDTO> actualizarUsuarioPorEmail(@PathVariable String email, @Valid @RequestBody Usuario usuarioActualizado) {
        String decodedEmail = URLDecoder.decode(email, StandardCharsets.UTF_8);
        Usuario actualizado = usuarioService.actualizarPorEmail(decodedEmail, usuarioActualizado);
        return ResponseEntity.ok(convertirADTO(actualizado));
    }

    @PreAuthorize("#email == authentication.principal.username")
    @PutMapping("/email/{email}/cambiar-password")
    public ResponseEntity<String> cambiarPassword(
            @PathVariable String email,
            @RequestBody CambiarPasswordRequest request) {
        String decodedEmail = URLDecoder.decode(email, StandardCharsets.UTF_8);
        usuarioService.cambiarPassword(decodedEmail, request.getActual(), request.getNueva());
        return ResponseEntity.ok("Contraseña actualizada correctamente");
    }

    @PreAuthorize("hasRole('ADMIN') or #email == authentication.principal.username")
    @DeleteMapping("/email/{email}")
    public void eliminarUsuarioPorEmail(@PathVariable String email) {
        String decodedEmail = URLDecoder.decode(email, StandardCharsets.UTF_8);
        usuarioService.eliminarPorEmail(decodedEmail);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin")
    public ResponseEntity<UsuarioDTO> crearUsuarioComoAdmin(@RequestBody UsuarioAdminDTO dto) {
        Usuario creado = usuarioService.crearUsuarioComoAdmin(dto);
        return ResponseEntity.ok(new UsuarioDTO(
                creado.getId(),
                creado.getNombre(),
                creado.getApellido(),
                creado.getEmail(),
                creado.getTelefono(),
                creado.getDireccion(),
                creado.getCiudad(),
                creado.getCodigoPostal(),
                creado.getPais(),
                creado.getRol().getNombre()
        ));
    }
}





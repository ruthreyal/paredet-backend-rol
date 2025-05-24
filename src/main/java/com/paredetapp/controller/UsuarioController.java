package com.paredetapp.controller;

import com.paredetapp.dto.CambiarPasswordRequest;
import com.paredetapp.dto.UsuarioAdminDTO;
import com.paredetapp.dto.UsuarioDTO;
import com.paredetapp.model.Usuario;
import com.paredetapp.repository.UsuarioRepository;
import com.paredetapp.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;



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

    @GetMapping("/email-existe")
    public ResponseEntity<Boolean> emailExiste(@RequestParam String email) {
        boolean existe = usuarioService.emailYaExiste(email);
        return ResponseEntity.ok(existe);
    }

    @PutMapping("/usuarios/cambiar-email")
    public ResponseEntity<?> cambiarEmail(
            @RequestBody Map<String, String> payload,
            @RequestHeader("Authorization") String token
    ) {
        String emailActual = payload.get("emailActual");
        String nuevoEmail = payload.get("nuevoEmail");
        String password = payload.get("password");

        if (emailActual == null || nuevoEmail == null || password == null) {
            return ResponseEntity.badRequest().body("Faltan campos obligatorios");
        }

        // Comprobamos si el nuevo email ya existe
        if (usuarioRepository.findByEmail(nuevoEmail).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El nuevo email ya está registrado");
        }

        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(emailActual);
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }

        Usuario usuario = usuarioOpt.get();
        if (!passwordEncoder.matches(password, usuario.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Contraseña incorrecta");
        }

        usuario.setEmail(nuevoEmail);
        usuarioRepository.save(usuario);

        return ResponseEntity.ok("Email actualizado correctamente. Por favor, vuelve a iniciar sesión.");
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
        try {
            String decodedEmail = URLDecoder.decode(email, StandardCharsets.UTF_8);
            System.out.println("Email recibido: " + decodedEmail);
            System.out.println("Datos recibidos para actualizar: " + usuarioActualizado);

            Usuario actualizado = usuarioService.actualizarPorEmail(decodedEmail, usuarioActualizado);
            System.out.println("Usuario actualizado correctamente");

            return ResponseEntity.ok(convertirADTO(actualizado));
        } catch (Exception e) {
            System.err.println("Error al actualizar usuario: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
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





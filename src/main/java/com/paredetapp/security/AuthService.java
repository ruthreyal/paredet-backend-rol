// AuthService.java
package com.paredetapp.security;

import com.paredetapp.model.Usuario;
import com.paredetapp.model.Rol;
import com.paredetapp.repository.UsuarioRepository;
import com.paredetapp.repository.RolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public String register(Usuario request) {
        request.setPassword(passwordEncoder.encode(request.getPassword()));

        Rol rolAsignado = (request.getRol() != null && "ADMIN".equalsIgnoreCase(request.getRol().getNombre()))
                ? rolRepository.findByNombre("ADMIN").orElseThrow(() -> new RuntimeException("Rol ADMIN no encontrado"))
                : rolRepository.findByNombre("USER").orElseThrow(() -> new RuntimeException("Rol USER no encontrado"));

        request.setRol(rolAsignado);
        usuarioRepository.save(request);

        return jwtService.generateToken(request.getEmail(), request.getNombre(), rolAsignado.getNombre());
    }

    public String login(String email, String password) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return jwtService.generateToken(usuario.getEmail(), usuario.getNombre(), usuario.getRol().getNombre());
    }

    /**
     * Envía un enlace de recuperación de contraseña con un token único al email del usuario (simulado por consola).
     */
    public void enviarEnlaceRecuperacion(String email) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            String token = UUID.randomUUID().toString();
            usuario.setTokenRecuperacion(token);
            usuario.setFechaExpiracionToken(LocalDateTime.now().plusMinutes(30));
            usuarioRepository.save(usuario);

            // Simulación de envío de email
            String enlace = "http://localhost:3000/restablecer-password?token=" + token;
            System.out.println("📧 Enlace de recuperación enviado a " + email + ": " + enlace);
        }
    }

    /**
     * Restablece la contraseña del usuario si el token es válido y no ha expirado.
     */
    public void restablecerPassword(String token, String nuevaPassword) {
        Usuario usuario = usuarioRepository.findByTokenRecuperacion(token)
                .orElseThrow(() -> new RuntimeException("Token no válido"));

        if (usuario.getFechaExpiracionToken().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("El token ha expirado");
        }

        usuario.setPassword(passwordEncoder.encode(nuevaPassword));
        usuario.setTokenRecuperacion(null);
        usuario.setFechaExpiracionToken(null);
        usuarioRepository.save(usuario);
    }
}

















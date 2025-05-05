package com.paredetapp.security;

import com.paredetapp.model.Usuario;
import com.paredetapp.model.Rol;
import com.paredetapp.repository.UsuarioRepository;
import com.paredetapp.repository.RolRepository;
import com.paredetapp.service.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final EmailService emailService;

    public String register(Usuario request) {
        request.setPassword(passwordEncoder.encode(request.getPassword()));

        Rol rolAsignado;
        if (request.getRol() != null && "ADMIN".equalsIgnoreCase(request.getRol().getNombre())) {
            rolAsignado = rolRepository.findByNombre("ADMIN")
                    .orElseThrow(() -> new RuntimeException("Rol ADMIN no encontrado"));
        } else {
            rolAsignado = rolRepository.findByNombre("USER")
                    .orElseThrow(() -> new RuntimeException("Rol USER no encontrado"));
        }

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

    public void enviarEnlaceRecuperacion(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("No existe ningún usuario con ese email."));

        String token = UUID.randomUUID().toString();
        usuario.setTokenRecuperacion(token);
        usuario.setFechaExpiracionToken(LocalDateTime.now().plusMinutes(30));
        usuarioRepository.save(usuario);

        String enlace = "http://localhost:3000/restablecer-password?token=" + token;

        try {
            emailService.enviarCorreoHTML(usuario.getEmail(), "Recuperación de contraseña", enlace);
        } catch (MessagingException e) {
            throw new RuntimeException("Error al enviar el correo de recuperación", e);
        }
    }

    /**
     * Restablece la contraseña del usuario si el token es válido y no ha expirado.
     */
    public void restablecerPassword(String token, String nuevaPassword) {
        Usuario usuario = usuarioRepository.findByTokenRecuperacion(token)
                .orElseThrow(() -> new RuntimeException("Token inválido o expirado"));

        if (usuario.getFechaExpiracionToken().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("El token ha expirado. Solicita uno nuevo.");
        }

        usuario.setPassword(passwordEncoder.encode(nuevaPassword));
        usuario.setTokenRecuperacion(null);
        usuario.setFechaExpiracionToken(null);
        usuarioRepository.save(usuario);
    }
}

















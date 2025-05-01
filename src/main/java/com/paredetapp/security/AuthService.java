package com.paredetapp.security;

import com.paredetapp.model.Usuario;
import com.paredetapp.model.Rol;
import com.paredetapp.repository.UsuarioRepository;
import com.paredetapp.repository.RolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;

    /**
     * Registra un nuevo usuario y asigna el rol USER por defecto si no se especifica.
     * Devuelve un token JWT que incluye email, nombre y rol.
     */
    public String register(Usuario request) {
        request.setPassword(passwordEncoder.encode(request.getPassword()));

        Rol rolAsignado;
        if (request.getRol() != null && request.getRol().getNombre().equalsIgnoreCase("ADMIN")) {
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

    /**
     * Autentica al usuario con email y contraseña, y devuelve un token con sus datos.
     */
    public String login(String email, String password) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return jwtService.generateToken(usuario.getEmail(), usuario.getNombre(), usuario.getRol().getNombre());
    }
}

















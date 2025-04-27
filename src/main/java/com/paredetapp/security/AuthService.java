package com.paredetapp.security;

import com.paredetapp.model.Usuario;
import com.paredetapp.model.Rol;
import com.paredetapp.repository.UsuarioRepository;
import com.paredetapp.repository.RolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * Registra un nuevo usuario y asigna el rol USER por defecto si no se especifica.
     * Devuelve un token JWT generado tras el registro.
     */
    public String register(Usuario request) {
        if (request.getRol() == null) {
            Rol rolUser = rolRepository.findByNombre("USER")
                    .orElseThrow(() -> new IllegalArgumentException("Rol USER no encontrado"));
            request.setRol(rolUser);
        }

        usuarioRepository.save(request);

        return jwtService.generateToken(
                request.getId().toString(),
                request.getRol().getNombre()
        );
    }

    /**
     * Autentica al usuario con email y contraseña, y devuelve un token JWT.
     */
    public String login(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return jwtService.generateToken(
                usuario.getId().toString(),
                usuario.getRol().getNombre()
        );
    }
}

















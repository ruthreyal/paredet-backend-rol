package com.paredetapp.security;

import com.paredetapp.model.Usuario;
import com.paredetapp.repository.UsuarioRepository;
import com.paredetapp.exception.UsuarioNoEncontradoException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Carga los datos del usuario desde la base de datos a partir del email (username).
     * Añade el rol como autoridad en formato ROLE_ para compatibilidad con Spring Security.
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        Usuario usuario = usuarioRepository.findByEmailConRol(username.trim())
                .orElseThrow(() -> new UsuarioNoEncontradoException("Usuario no encontrado con email: " + username));

        String nombreRol = usuario.getRol().getNombre(); // ejemplo: "ADMIN"
        GrantedAuthority autoridad = new SimpleGrantedAuthority("ROLE_" + nombreRol);

        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getPassword())
                .authorities(Collections.singleton(autoridad))
                .build();
    }
}








package com.paredetapp.security;

import com.paredetapp.model.Usuario;
import com.paredetapp.repository.UsuarioRepository;
import com.paredetapp.exception.UsuarioNoEncontradoException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        System.out.println("🔍 Buscando usuario con email: [" + username + "]");
        Usuario usuario = usuarioRepository.findByEmail(username.trim())
                .orElseThrow(() -> new UsuarioNoEncontradoException("Usuario no encontrado con email: " + username));

        System.out.println("✅ Usuario encontrado: " + usuario.getEmail() + ", Rol: " + usuario.getRol());

        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getPassword())
                .roles(usuario.getRol().name()) // "USER" o "ADMIN"
                .build();
    }
}





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

    /**
     * Carga los datos del usuario desde la base de datos a partir del email (username).
     * Utiliza Spring Security para crear el UserDetails necesario para la autenticación.
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        // Buscar el usuario en base de datos
        Usuario usuario = usuarioRepository.findByEmailConRol(username.trim())
                .orElseThrow(() -> new UsuarioNoEncontradoException("Usuario no encontrado con email: " + username));


        // 🔎 LOG para depurar si el rol se está cargando correctamente
        System.out.println("➡️ Email: " + usuario.getEmail());
        System.out.println("➡️ Rol: " + (usuario.getRol() != null ? usuario.getRol().getNombre() : "NULL"));

        // Crear el objeto UserDetails con el email, contraseña y rol del usuario
        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getPassword())
                .roles(usuario.getRol().getNombre())
                .build();
    }

}






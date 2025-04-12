package com.paredetapp.security;

import com.paredetapp.model.Usuario;
import com.paredetapp.model.Usuario.Rol;
import com.paredetapp.repository.UsuarioRepository;
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
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService; // 👈 NUEVO

    public String register(Usuario request) {
        try {
            System.out.println("📥 Registrando nuevo usuario: " + request.getEmail());
            request.setPassword(passwordEncoder.encode(request.getPassword()));

            // Si no viene rol en la petición, se pone por defecto USER
            if (request.getRol() == null) {
                request.setRol(Rol.USER);
            }

            usuarioRepository.save(request);

            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
            String token = jwtService.generateToken(userDetails);

            return token;
        } catch (Exception e) {
            throw e;
        }
    }

    public String login(String email, String password) {
        try {
            System.out.println("📤 Intentando login con email: " + email);
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
            UserDetails user = (UserDetails) auth.getPrincipal();
            System.out.println("✅ Autenticación exitosa para: " + user.getUsername());
            user.getAuthorities().forEach(a -> System.out.println("🔐 Authority asignada: " + a.getAuthority()));

            // ✅ Usar el UserDetails para el token
            String token = jwtService.generateToken(user);
            System.out.println("🔐 Token generado: " + token);
            return token;
        } catch (Exception e) {
            System.out.println("❌ Error en login: " + e.getMessage());
            throw e;
        }
    }
}














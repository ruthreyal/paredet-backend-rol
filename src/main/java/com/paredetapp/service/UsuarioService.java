package com.paredetapp.service;

import com.paredetapp.dto.UsuarioAdminDTO;
import com.paredetapp.exception.UsuarioNoEncontradoException;
import com.paredetapp.model.Usuario;
import com.paredetapp.repository.RolRepository;
import com.paredetapp.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;

    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> obtenerPorId(UUID id) {
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> obtenerPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public Usuario guardarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public void eliminarUsuario(UUID id) {
        usuarioRepository.deleteById(id);
    }

    public void eliminarPorEmail(String email) {
        usuarioRepository.findByEmail(email)
                .ifPresentOrElse(
                        usuarioRepository::delete,
                        () -> {
                            throw new UsuarioNoEncontradoException("No se encontró ningún usuario con email: " + email);
                        });
    }

    public Usuario actualizarUsuario(UUID id, Usuario usuarioActualizado) {
        return usuarioRepository.findById(id)
                .map(usuarioExistente -> {
                    usuarioExistente.setNombre(usuarioActualizado.getNombre());
                    usuarioExistente.setApellido(usuarioActualizado.getApellido());
                    usuarioExistente.setTelefono(usuarioActualizado.getTelefono());
                    usuarioExistente.setDireccion(usuarioActualizado.getDireccion());
                    usuarioExistente.setCiudad(usuarioActualizado.getCiudad());
                    usuarioExistente.setCodigoPostal(usuarioActualizado.getCodigoPostal());
                    usuarioExistente.setPais(usuarioActualizado.getPais());
                    return usuarioRepository.save(usuarioExistente);
                })
                .orElseThrow(() -> new UsuarioNoEncontradoException("Usuario no encontrado con ID: " + id));
    }

    public Usuario actualizarPorEmail(String email, Usuario usuarioActualizado) {
        return usuarioRepository.findByEmail(email)
                .map(usuarioExistente -> {
                    usuarioExistente.setNombre(usuarioActualizado.getNombre());
                    usuarioExistente.setApellido(usuarioActualizado.getApellido());
                    usuarioExistente.setTelefono(usuarioActualizado.getTelefono());
                    usuarioExistente.setDireccion(usuarioActualizado.getDireccion());
                    usuarioExistente.setCiudad(usuarioActualizado.getCiudad());
                    usuarioExistente.setCodigoPostal(usuarioActualizado.getCodigoPostal());
                    usuarioExistente.setPais(usuarioActualizado.getPais());

                    if (usuarioActualizado.getRol() != null && usuarioActualizado.getRol().getNombre() != null) {
                        rolRepository.findByNombre(usuarioActualizado.getRol().getNombre())
                                .ifPresent(usuarioExistente::setRol);
                    }

                    return usuarioRepository.save(usuarioExistente);
                })
                .orElseThrow(() -> new UsuarioNoEncontradoException("Usuario no encontrado con email: " + email));
    }

    public boolean emailYaExiste(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public boolean esPropietario(UUID id, String email) {
        return usuarioRepository.findById(id)
                .map(u -> u.getEmail().equalsIgnoreCase(email))
                .orElse(false);
    }

        private final PasswordEncoder passwordEncoder;

        public void cambiarPassword(String email, String actual, String nueva) {
            Usuario usuario = usuarioRepository.findByEmail(email)
                    .orElseThrow(() -> new UsuarioNoEncontradoException("Usuario no encontrado"));

            if (!passwordEncoder.matches(actual, usuario.getPassword())) {
                throw new RuntimeException("La contraseña actual no es correcta");
            }

            usuario.setPassword(passwordEncoder.encode(nueva));
            usuarioRepository.save(usuario);
        }

    public Usuario crearUsuarioComoAdmin(UsuarioAdminDTO dto) {
        System.out.println("✉️ Registro solicitado para: " + dto.getEmail() + " con rol " + dto.getRolNombre());

        if (usuarioRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Ya existe un usuario con ese email");
        }

        Usuario nuevo = new Usuario();
        nuevo.setNombre(dto.getNombre());
        nuevo.setApellido(dto.getApellido());
        nuevo.setEmail(dto.getEmail());
        nuevo.setPassword(passwordEncoder.encode(dto.getPassword()));
        nuevo.setTelefono(dto.getTelefono());
        nuevo.setDireccion(dto.getDireccion());
        nuevo.setCiudad(dto.getCiudad());
        nuevo.setCodigoPostal(dto.getCodigoPostal());
        nuevo.setPais(dto.getPais());

        var rol = rolRepository.findByNombre(dto.getRolNombre().toUpperCase())
                .orElseThrow(() -> {
                    System.out.println("❌ Rol no encontrado: " + dto.getRolNombre());
                    return new RuntimeException("Rol no válido");
                });

        nuevo.setRol(rol);

        return usuarioRepository.save(nuevo);
    }


}




package com.paredetapp.service;

import com.paredetapp.model.Rol;
import com.paredetapp.model.Usuario;
import com.paredetapp.repository.RolRepository;
import com.paredetapp.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
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
                .ifPresent(usuarioRepository::delete);
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
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
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

                    // 👉 Nuevo: actualizar el rol si se incluye en el JSON
                    if (usuarioActualizado.getRol() != null && usuarioActualizado.getRol().getNombre() != null) {
                        rolRepository.findByNombre(usuarioActualizado.getRol().getNombre())
                                .ifPresent(usuarioExistente::setRol);
                    }

                    return usuarioRepository.save(usuarioExistente);
                })
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con email: " + email));
    }

    public boolean esPropietario(UUID id, String email) {
        return usuarioRepository.findById(id)
                .map(u -> u.getEmail().equalsIgnoreCase(email))
                .orElse(false);
    }
}



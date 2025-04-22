package com.paredetapp.service;

import com.paredetapp.model.Usuario;
import com.paredetapp.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> obtenerPorId(UUID id) {
        return usuarioRepository.findById(id);
    }

    public Usuario guardarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public void eliminarUsuario(UUID id) {
        usuarioRepository.deleteById(id);
    }

    public Optional<Usuario> obtenerPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    /**
     * Actualiza los datos personales de un usuario existente.
     * No actualiza email ni contraseña por seguridad.
     */
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

}

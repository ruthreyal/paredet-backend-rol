package com.paredetapp.controller;

import com.paredetapp.model.Direccion;
import com.paredetapp.service.DireccionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/direcciones")
@RequiredArgsConstructor
public class DireccionController {

    private final DireccionService direccionService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<Direccion> listarDirecciones() {
        return direccionService.obtenerTodas();
    }

    @PreAuthorize("hasRole('ADMIN') or @direccionService.esPropietario(#id, #principal.username)")
    @GetMapping("/{id}")
    public Optional<Direccion> obtenerDireccion(@PathVariable UUID id, @AuthenticationPrincipal UserDetails principal) {
        return direccionService.obtenerPorId(id);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public Direccion crearDireccion(@RequestBody Direccion direccion) {
        return direccionService.guardarDireccion(direccion);
    }

    @PreAuthorize("hasRole('ADMIN') or @direccionService.esPropietario(#id, #principal.username)")
    @DeleteMapping("/{id}")
    public void eliminarDireccion(@PathVariable UUID id, @AuthenticationPrincipal UserDetails principal) {
        direccionService.eliminarDireccion(id);
    }
}





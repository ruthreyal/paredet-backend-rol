package com.paredetapp.controller;

import com.paredetapp.model.Coleccion;
import com.paredetapp.service.ColeccionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/colecciones")
@RequiredArgsConstructor
public class ColeccionController {

    private final ColeccionService coleccionService;

    /**
     * Lista todas las colecciones (acceso público).
     */
    @GetMapping
    public List<Coleccion> listarColecciones() {
        return coleccionService.obtenerTodas();
    }

    /**
     * Obtiene una colección por ID (acceso público).
     */
    @GetMapping("/{id}")
    public Optional<Coleccion> obtenerColeccion(@PathVariable UUID id) {
        return coleccionService.obtenerPorId(id);
    }

    /**
     * Crea una nueva colección (solo ADMIN).
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Coleccion crearColeccion(@RequestBody Coleccion coleccion) {
        return coleccionService.guardar(coleccion);
    }

    /**
     * Elimina una colección por ID (solo ADMIN).
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void eliminarColeccion(@PathVariable UUID id) {
        coleccionService.eliminar(id);
    }
}





package com.paredetapp.controller;

import com.paredetapp.model.Direccion;
import com.paredetapp.service.DireccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/direcciones")
public class DireccionController {

    @Autowired
    private DireccionService direccionService;

    @GetMapping
    public List<Direccion> listarDirecciones() {
        return direccionService.obtenerTodas();
    }

    @GetMapping("/{id}")
    public Optional<Direccion> obtenerDireccion(@PathVariable UUID id) {
        return direccionService.obtenerPorId(id);
    }

    @PostMapping
    public Direccion crearDireccion(@RequestBody Direccion direccion) {
        return direccionService.guardarDireccion(direccion);
    }

    @DeleteMapping("/{id}")
    public void eliminarDireccion(@PathVariable UUID id) {
        direccionService.eliminarDireccion(id);
    }
}


package com.paredetapp.controller;

import com.paredetapp.model.Coleccion;
import com.paredetapp.service.ColeccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/colecciones")
public class ColeccionController {

    @Autowired
    private ColeccionService coleccionService;

    @GetMapping
    public List<Coleccion> listarColecciones() {
        return coleccionService.obtenerTodas();
    }

    @GetMapping("/{id}")
    public Optional<Coleccion> obtenerColeccion(@PathVariable UUID id) {
        return coleccionService.obtenerPorId(id);
    }

    @PostMapping
    public Coleccion crearColeccion(@RequestBody Coleccion coleccion) {
        return coleccionService.guardar(coleccion);
    }

    @DeleteMapping("/{id}")
    public void eliminarColeccion(@PathVariable UUID id) {
        coleccionService.eliminar(id);
    }
}



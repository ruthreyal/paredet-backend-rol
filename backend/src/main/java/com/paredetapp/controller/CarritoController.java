package com.paredetapp.controller;

import com.paredetapp.model.Carrito;
import com.paredetapp.service.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/carritos")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    @GetMapping
    public List<Carrito> listarCarritos() {
        return carritoService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public Optional<Carrito> obtenerCarrito(@PathVariable UUID id) {
        return carritoService.obtenerPorId(id);
    }

    @PostMapping
    public Carrito crearCarrito(@RequestBody Carrito carrito) {
        return carritoService.guardarCarrito(carrito);
    }

    @DeleteMapping("/{id}")
    public void eliminarCarrito(@PathVariable UUID id) {
        carritoService.eliminarCarrito(id);
    }
}


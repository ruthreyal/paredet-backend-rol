package com.paredetapp.controller;

import com.paredetapp.model.Categoria;
import com.paredetapp.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;

    /**
     * Crea una nueva categoría (solo para ADMIN).
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Categoria> crearCategoria(@RequestBody Categoria categoria) {
        Categoria nueva = categoriaService.saveCategoria(categoria);
        return ResponseEntity.ok(nueva);
    }

    /**
     * Lista todas las categorías (acceso completamente público).
     */
    @GetMapping
    public ResponseEntity<List<Categoria>> listarCategorias() {
        return ResponseEntity.ok(categoriaService.getAllCategorias());
    }
}





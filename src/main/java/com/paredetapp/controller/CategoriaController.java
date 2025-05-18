package com.paredetapp.controller;

import com.paredetapp.dto.CategoriaDTO;
import com.paredetapp.mapper.CategoriaMapper;
import com.paredetapp.model.Categoria;
import com.paredetapp.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;

    @GetMapping
    public List<CategoriaDTO> listarCategorias() {
        return categoriaService.obtenerTodas()
                .stream()
                .map(CategoriaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public CategoriaDTO crearCategoria(@RequestBody CategoriaDTO dto) {
        Categoria categoria = CategoriaMapper.toEntity(dto);
        Categoria guardada = categoriaService.guardar(categoria);
        return CategoriaMapper.toDTO(guardada);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void eliminarCategoria(@PathVariable UUID id) {
        categoriaService.eliminarPorId(id);
    }
}






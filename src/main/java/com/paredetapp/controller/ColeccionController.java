package com.paredetapp.controller;

import com.paredetapp.dto.ColeccionDTO;
import com.paredetapp.mapper.ColeccionMapper;
import com.paredetapp.model.Coleccion;
import com.paredetapp.service.ColeccionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/colecciones")
@RequiredArgsConstructor
public class ColeccionController {

    private final ColeccionService coleccionService;

    @GetMapping
    public List<ColeccionDTO> listarColecciones() {
        return coleccionService.obtenerTodas()
                .stream()
                .map(ColeccionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ColeccionDTO crearColeccion(@RequestBody ColeccionDTO dto) {
        Coleccion coleccion = ColeccionMapper.toEntity(dto);
        Coleccion guardada = coleccionService.guardar(coleccion);
        return ColeccionMapper.toDTO(guardada);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void eliminarColeccion(@PathVariable UUID id) {
        coleccionService.eliminarPorId(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ColeccionDTO actualizarColeccion(@PathVariable UUID id, @RequestBody ColeccionDTO dto) {
        Coleccion coleccion = coleccionService.obtenerPorId(id);
        coleccion.setNombre(dto.getNombre());
        Coleccion actualizada = coleccionService.guardar(coleccion);
        return ColeccionMapper.toDTO(actualizada);
    }

}





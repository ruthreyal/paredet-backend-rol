package com.paredetapp.service;

import com.paredetapp.model.Categoria;
import com.paredetapp.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaService {


    private final CategoriaRepository categoriaRepository;

    public Categoria saveCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public List<Categoria> getAllCategorias() {
        return categoriaRepository.findAll();
    }
}



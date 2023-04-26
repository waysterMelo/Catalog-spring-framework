package com.wayster.catalog.services;

import com.wayster.catalog.dto.CategoryDTO;
import com.wayster.catalog.entity.CategoryEntity;
import com.wayster.catalog.repositories.CategoryRepository;
import com.wayster.catalog.services.servicesExceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll() {
        List<CategoryEntity> lista = categoryRepository.findAll();
        return lista.stream().map(CategoryDTO::new).collect(Collectors.toList());
     }


     @Transactional(readOnly = true)
    public CategoryDTO findById(Long id){
         Optional<CategoryEntity> rs = categoryRepository.findById(id);
         CategoryEntity entity = rs.orElseThrow(() -> new EntityNotFoundException("Entity not found"));
         return new CategoryDTO(entity);
     }


}

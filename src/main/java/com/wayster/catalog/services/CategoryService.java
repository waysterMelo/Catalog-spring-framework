package com.wayster.catalog.services;

import com.wayster.catalog.dto.CategoryDTO;
import com.wayster.catalog.entity.CategoryEntity;
import com.wayster.catalog.repositories.CategoryRepository;
import com.wayster.catalog.services.servicesExceptions.DatabaseException;
import com.wayster.catalog.services.servicesExceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
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
         CategoryEntity entity = rs.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
         return new CategoryDTO(entity);
     }


     @Transactional
    public CategoryDTO insert(CategoryDTO dto) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName(dto.getNome());
        categoryEntity = categoryRepository.save(categoryEntity);
        return new CategoryDTO(categoryEntity);
    }

    @Transactional
    public CategoryDTO update (CategoryDTO dto, Long id){
       try {
           CategoryEntity entity = categoryRepository.getOne(id);
           entity.setName(dto.getNome());
           entity = categoryRepository.save(entity);
           return new CategoryDTO(entity);

       }catch (EntityNotFoundException ex){
           throw new ResourceNotFoundException("Id not found" + id);
       }

    }

    @Transactional
    public void delete(Long id) {
        try {
            categoryRepository.deleteById(id);
        }catch (EmptyResultDataAccessException ex){
                throw new ResourceNotFoundException("Id not found" + id);
        }catch (DataIntegrityViolationException data_ex){
            throw new DatabaseException("Integrity Violation");
        }
    }

    @Transactional(readOnly = true)
    public Page<CategoryDTO> findAllPaged(Pageable pageable) {
        Page<CategoryEntity> list = categoryRepository.findAll(pageable);
        return list.map(CategoryDTO::new);
    }
}

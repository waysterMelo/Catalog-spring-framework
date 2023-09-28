package com.wayster.catalog.services;

import com.wayster.catalog.dto.CategoryDTO;
import com.wayster.catalog.dto.ProductDto;
import com.wayster.catalog.entity.CategoryEntity;
import com.wayster.catalog.entity.ProductEntity;
import com.wayster.catalog.repositories.CategoryRepository;
import com.wayster.catalog.repositories.ProductRepository;
import com.wayster.catalog.services.servicesExceptions.DatabaseException;
import com.wayster.catalog.services.servicesExceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<ProductDto> findAll() {
        List<ProductEntity> lista = productRepository.findAll();
        return lista.stream().map(ProductDto::new).collect(Collectors.toList());
     }


     @Transactional(readOnly = true)
    public ProductDto findById(Long id){
         Optional<ProductEntity> rs = productRepository.findById(id);
         ProductEntity entity = rs.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
         return new ProductDto(entity, entity.getCategories());
     }


     @Transactional
    public ProductDto insert(ProductDto dto) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(dto.getName());
        productEntity = productRepository.save(productEntity);
        return new ProductDto(productEntity);
    }

    @Transactional
    public ProductDto update (ProductDto dto, Long id){
       try {
           ProductEntity entity = productRepository.getReferenceById(id);
           entity.setName(dto.getName());
           entity = productRepository.save(entity);
           return new ProductDto(entity);

       }catch (EntityNotFoundException ex){
           throw new ResourceNotFoundException("Id not found" + id);
       }

    }

    @Transactional
    public void delete(Long id) {
        try {
            productRepository.deleteById(id);
        }catch (EmptyResultDataAccessException ex){
                throw new ResourceNotFoundException("Id not found" + id);
        }catch (DataIntegrityViolationException data_ex){
            throw new DatabaseException("Integrity Violation");
        }
    }

    @Transactional(readOnly = true)
    public Page<ProductDto> findAllPaged(PageRequest pageRequest) {
        Page<ProductEntity> list = productRepository.findAll(pageRequest);
        return list.map(x -> new ProductDto(x));
    }
}

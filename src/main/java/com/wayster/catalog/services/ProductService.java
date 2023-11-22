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
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;


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
        copyDtoToEntity(dto, productEntity);
        productEntity = productRepository.save(productEntity);
        return new ProductDto(productEntity);
    }

    private void copyDtoToEntity(ProductDto dto, ProductEntity productEntity) {
        productEntity.setName(dto.getName());
        productEntity.setDescription(dto.getDescription());
        productEntity.setDate(dto.getDate());
        productEntity.setImgUrl(dto.getImg_Url());
        productEntity.setPrice(dto.getPrice());

        productEntity.getCategories().clear();

        for (CategoryDTO catDto : dto.getCategories()){
            CategoryEntity categoryEntity = categoryRepository.getOne(catDto.getId());
            productEntity.getCategories().add(categoryEntity);
        }

    }

    @Transactional
    public ProductDto update (ProductDto dto, Long id){
       try {
           ProductEntity entity = productRepository.getOne(id);
           copyDtoToEntity(dto, entity);
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
    public Page<ProductDto> findAllPaged(Pageable pageable) {
        Page<ProductEntity> list = productRepository.findAll(pageable);
        return list.map(x -> new ProductDto(x));
    }
}

package com.wayster.catalog.repositories;

import com.wayster.catalog.entity.ProductEntity;
import com.wayster.catalog.services.ProductService;
import com.wayster.catalog.services.servicesExceptions.ResourceNotFoundException;
import com.wayster.catalog.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class ProductRepositoryTest {
    private Long existId;
    private long nonExistId;
    private long countTotalProducts;
    private long dependentId;
    private PageImpl<ProductEntity> page;
    private ProductEntity product;

    @InjectMocks
    private ProductService productService;
    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() throws Exception{
        existId = 1L;
        nonExistId = 250L;
        countTotalProducts = 25L;
        dependentId = 3L;
        product = Factory.createProduct();
        page = new PageImpl<>(List.of(product));

        Mockito.when(productRepository.findAll((Pageable) ArgumentMatchers.any())).thenReturn(page);
        Mockito.when(productRepository.save(ArgumentMatchers.any())).thenReturn(page);
        Mockito.when(productRepository.findById(existId)).thenReturn(Optional.of(product));
        Mockito.when(productRepository.findById(nonExistId)).thenReturn(Optional.empty());

        Mockito.doNothing().when(productRepository).deleteById(existId);
        Mockito.doThrow(EmptyResultDataAccessException.class).when(productRepository).deleteById(nonExistId);
    }

    @Test
    public void deleteShouldDeleteObjectWhenIdExists(){
        productRepository.deleteById(existId);
        Optional<ProductEntity> result = productRepository.findById(existId);
        Assertions.assertFalse(result.isPresent());

    }

    @Test
    public  void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist(){
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            productService.delete(nonExistId);
        });
        Mockito.verify(productRepository, Mockito.times(1)).deleteById(nonExistId);

    }
    @Test
    public void testSaveWithIdNull(){
        ProductEntity productEntity = Factory.createProduct();
        productEntity.setId(null);
        productEntity = productRepository.save(productEntity);

        Assertions.assertNotNull(productEntity.getId());
        Assertions.assertEquals(countTotalProducts + 1, productEntity.getId());
    }

}

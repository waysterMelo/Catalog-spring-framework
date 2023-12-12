package com.wayster.catalog.tests;

import com.wayster.catalog.dto.ProductDto;
import com.wayster.catalog.entity.CategoryEntity;
import com.wayster.catalog.entity.ProductEntity;

import java.time.Instant;

public class Factory {


    public static ProductEntity createProduct(){
            ProductEntity productEntity = new ProductEntity(1l, "Phone", "Good Phone", 800.00,
                    "https://img.com/img.png", Instant.parse("2020-10-20T03:00:00Z"));
            productEntity.getCategories().add(new CategoryEntity(2L,"Eletronics"));
            return productEntity;
    }

    public static ProductDto createProductDto(){
        ProductEntity product = createProduct();
        return new ProductDto(product, product.getCategories());
    }
}

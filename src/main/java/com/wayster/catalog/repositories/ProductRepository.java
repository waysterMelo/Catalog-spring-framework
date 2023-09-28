package com.wayster.catalog.repositories;


import com.wayster.catalog.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {


}

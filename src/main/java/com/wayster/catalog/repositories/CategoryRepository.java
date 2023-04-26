package com.wayster.catalog.repositories;

import com.wayster.catalog.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {


}

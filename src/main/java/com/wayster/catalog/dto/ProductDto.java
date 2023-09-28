package com.wayster.catalog.dto;

import com.wayster.catalog.entity.CategoryEntity;
import com.wayster.catalog.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private String img_Url;
    private Instant date;

    private List<CategoryDTO> categories = new ArrayList<>();


    public ProductDto(ProductEntity entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.price = entity.getPrice();
        this.img_Url = entity.getImgUrl();
        this.date = entity.getDate();


    }

    public ProductDto(ProductEntity entity, Set<CategoryEntity> categoryEntities) {
        this(entity);
        categories.forEach(cat -> this.categories.add(new CategoryDTO(cat)));

    }


}

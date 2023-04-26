package com.wayster.catalog.dto;

import com.wayster.catalog.entity.CategoryEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {

    private Long id;
    private String nome;


    public CategoryDTO(CategoryEntity entity){
        this.id = entity.getId();
        this.nome = entity.getNome();
    }
}

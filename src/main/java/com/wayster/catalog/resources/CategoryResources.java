package com.wayster.catalog.resources;

import com.wayster.catalog.dto.CategoryDTO;
import com.wayster.catalog.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResources {

    @Autowired
    private CategoryService categoryService;
    
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> findAll(){
        List<CategoryDTO> lista = categoryService.findAll();
        return ResponseEntity.ok(lista);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CategoryDTO> findById(@PathVariable Long id){
        CategoryDTO categoryId = categoryService.findById(id);
        return ResponseEntity.ok(categoryId);
    }
    
}

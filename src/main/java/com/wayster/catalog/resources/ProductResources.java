package com.wayster.catalog.resources;

import com.wayster.catalog.dto.CategoryDTO;
import com.wayster.catalog.dto.ProductDto;
import com.wayster.catalog.services.CategoryService;
import com.wayster.catalog.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/products")
public class ProductResources {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<Page<ProductDto>> findAll(Pageable pageable){
        Page<ProductDto> lista = productService.findAllPaged(pageable);
        return ResponseEntity.ok().body(lista);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductDto> findById(@PathVariable Long id){
        ProductDto productId = productService.findById(id);
        return ResponseEntity.ok(productId);
    }

    @PostMapping
    public ResponseEntity<ProductDto> insert(@RequestBody ProductDto dto){
        dto = productService.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductDto> update(@PathVariable Long id, @RequestBody ProductDto dto){
        dto =  productService.update(dto, id);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

}

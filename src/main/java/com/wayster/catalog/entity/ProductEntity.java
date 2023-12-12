package com.wayster.catalog.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Data
@EqualsAndHashCode
@Entity
@Table(name = "tb_product")
public class ProductEntity implements Serializable {
    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String name;
    @Column(length = 455)
    private String description;
    private Double price;
    private String imgUrl;
    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant date;


    @ManyToMany
            @JoinTable(name = "tb_product_category", joinColumns = @JoinColumn(name = "product_id"),
                    inverseJoinColumns = @JoinColumn(name = "category_id"))
    Set<CategoryEntity> categories = new HashSet<>();

    public ProductEntity() {
    }

    public ProductEntity(long l, String phone, String goodPhone, double v, String url, Instant parse) {

    }
}

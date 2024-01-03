package com.wayster.catalog.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
@Table(name = "tb_category")
public class CategoryEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant created_At;

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant updateAt;

    @ManyToMany(mappedBy = "categories")
    private Set<ProductEntity> product = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryEntity other = (CategoryEntity) o;
        if (id == null) {
            if (other.id != null)
                return false;
        }else if(!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

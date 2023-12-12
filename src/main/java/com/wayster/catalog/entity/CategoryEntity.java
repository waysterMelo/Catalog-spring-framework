package com.wayster.catalog.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "tb_category")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoryEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Instant created_At;

    public CategoryEntity(long l, String eletronics) {
    }


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

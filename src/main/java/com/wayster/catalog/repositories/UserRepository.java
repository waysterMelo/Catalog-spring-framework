package com.wayster.catalog.repositories;


import com.wayster.catalog.entity.ProductEntity;
import com.wayster.catalog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


}

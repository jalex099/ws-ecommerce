package com.javidev.ecommerce.repositories;

import com.javidev.ecommerce.entities.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
    // Name to lower case

    @Query("FROM Product p WHERE (:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))) " +
            "AND (:category IS NULL OR p.category.id = :category) " +
            "AND (:isActive IS NULL OR p.isActive = :isActive)")
    ArrayList<Product> findByFilters(String name, Long category, Boolean isActive);
}

package com.javidev.ecommerce.repositories;

import com.javidev.ecommerce.entities.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
    @Query("FROM Product p WHERE (:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))) " +
            "AND (:category IS NULL OR p.categoryId = :category) " +
            "AND (:isActive IS NULL OR p.isActive = :isActive)" +
            "ORDER BY p.id ASC")
    ArrayList<Product> findByFilters(String name, Long category, Boolean isActive);

}

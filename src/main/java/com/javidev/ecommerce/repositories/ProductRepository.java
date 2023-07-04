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
            "AND (:isActive IS NULL OR p.isActive = :isActive)")
    ArrayList<Product> findByFilters(String name, Long category, Boolean isActive);


    @Modifying
    @Query(value = "INSERT INTO products (name, description, order_pos, price, category_id, company_id) " +
            "VALUES (:name, :description, (SELECT MAX(p.order_pos) + 1 FROM products p WHERE company_id = :companyId), :price, :categoryId, :companyId) ", nativeQuery = true)
    @Transactional
    void save(String name, String description, Double price, Long categoryId, Long companyId);
}

package com.javidev.ecommerce.repositories;

import com.javidev.ecommerce.entities.Category;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {
    @Query("FROM Category c WHERE (:name IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))) " +
            "AND (:isActive IS NULL OR c.isActive = :isActive)")
    ArrayList<Category> findByFilters(String name, Boolean isActive);


    @Modifying
    @Query(value = "INSERT INTO categories (name, description, company_id) VALUES (:name, :description, :companyId) ", nativeQuery = true)
    @Transactional
    void save(String name, String description, Long companyId);
}

package com.javidev.ecommerce.repositories;

import com.javidev.ecommerce.entities.ProductOption;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface ProductOptionRepository extends CrudRepository<ProductOption, Long> {

    @Query(value = "FROM products_options WHERE product_id = :productId", nativeQuery = true)
    ArrayList<ProductOption> findByProductId(Long productId);

    @Modifying
    @Query(value = "INSERT INTO products_options (name, description, aditional_price, product_id, company_id) " +
            "VALUES (:name, :description, :aditionalPrice, :productId, :companyId) ", nativeQuery = true)
    @Transactional
    void save(String name, String description, Double aditionalPrice, Long productId, Long companyId);
}

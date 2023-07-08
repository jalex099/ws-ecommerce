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
    @Query(value = "INSERT INTO products_options (name, description, order_pos, product_id, company_id) " +
            "VALUES (:name, :description, (SELECT MAX(po.order_pos) + 1 FROM products_options po WHERE company_id = :companyId)," +
            " :productId, :companyId) ", nativeQuery = true)
    @Transactional
    void save(String name, String description, Long productId, Long companyId);
}

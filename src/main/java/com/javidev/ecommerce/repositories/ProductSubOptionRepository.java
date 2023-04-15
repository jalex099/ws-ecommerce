package com.javidev.ecommerce.repositories;

import com.javidev.ecommerce.entities.ProductOption;
import com.javidev.ecommerce.entities.ProductSubOption;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductSubOptionRepository extends CrudRepository<ProductSubOption, Long> {

    @Modifying
    @Query(value = "INSERT INTO products_sub_options (name, description, aditional_price, product_option_id, company_id) " +
            "VALUES (:name, :description, :aditionalPrice, :productOptionId, :companyId) ", nativeQuery = true)
    @Transactional
    void save(String name, String description, Double aditionalPrice, Long productOptionId, Long companyId);
}

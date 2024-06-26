package com.javidev.ecommerce.repositories;

import com.javidev.ecommerce.config.Params;
import com.javidev.ecommerce.entities.Cart;
import org.hibernate.annotations.Table;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface CartRepository extends CrudRepository<Cart, Long> {

    @Query(value = "SELECT * FROM carts WHERE user_id = ?1 AND status = 'ACT' ORDER BY date DESC", nativeQuery = true)
    ArrayList<Cart> findByUserId(Long userId);

    @Query(value = "SELECT * FROM carts WHERE code = ?1 AND visibility='PUBLIC' AND status = 'ACT' LIMIT 1", nativeQuery = true)
    Cart findByCode(String code);
}

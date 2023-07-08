package com.javidev.ecommerce.repositories;

import com.javidev.ecommerce.entities.Cart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends CrudRepository<Cart, Long> {

}

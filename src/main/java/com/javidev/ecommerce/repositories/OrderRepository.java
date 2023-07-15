package com.javidev.ecommerce.repositories;

import com.javidev.ecommerce.entities.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
}

package com.javidev.ecommerce.repositories;

import com.javidev.ecommerce.entities.Test;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends CrudRepository<Test, Long> {

}

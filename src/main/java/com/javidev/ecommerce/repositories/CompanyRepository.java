package com.javidev.ecommerce.repositories;

import com.javidev.ecommerce.entities.Company;
import org.springframework.data.repository.CrudRepository;

public interface CompanyRepository extends CrudRepository<Company, Long> {

}

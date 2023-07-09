package com.javidev.ecommerce.repositories;

import com.javidev.ecommerce.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    @Modifying
    @Query(value = "INSERT INTO users (first_name, last_name, email, password, company_id) VALUES (:firstName, :lastName, :email, :password, :companyId) ", nativeQuery = true)
    @Transactional
    void save(String firstName, String lastName, String email, String password, Long companyId);

    Optional<User> findOneByEmail(String email);
}

package com.javidev.ecommerce.services;

import com.javidev.ecommerce.config.Params;
import com.javidev.ecommerce.entities.User;
import com.javidev.ecommerce.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Iterable<User> getUsers() {
        return userRepository.findAll();
    }

    public void createUser(String firstName, String lastName, String email, String password) {
        userRepository.save(firstName, lastName, email, password, Long.parseLong(Params.COMPANY_ID));
    }
}

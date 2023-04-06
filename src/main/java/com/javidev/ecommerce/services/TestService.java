package com.javidev.ecommerce.services;

import com.javidev.ecommerce.entities.Test;
import com.javidev.ecommerce.repositories.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class TestService {

    @Autowired
    private TestRepository testRepository;

    public ArrayList<Test> getTests() {
        return (ArrayList<Test>) testRepository.findAll();
    }
}

package com.javidev.ecommerce.controllers;

import com.javidev.ecommerce.entities.Test;
import com.javidev.ecommerce.services.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestService testService;


    @GetMapping("")
    public ArrayList<Test> test() {
        return testService.getTests();
    }
}

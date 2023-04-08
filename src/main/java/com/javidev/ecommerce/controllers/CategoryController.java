package com.javidev.ecommerce.controllers;

import com.javidev.ecommerce.entities.Category;
import com.javidev.ecommerce.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ArrayList<Category> getCategories(
            @RequestParam(value = "name", required = false) String name ,
            @RequestParam(value = "is_active", required = false) Boolean isActive
    ) {
        return categoryService.getCategories(name, isActive);
    }
}

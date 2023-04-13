package com.javidev.ecommerce.controllers;

import com.javidev.ecommerce.config.Params;
import com.javidev.ecommerce.entities.Category;
import com.javidev.ecommerce.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

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

    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody HashMap<String, Object> data) {
        try{
            categoryService.createCategory(data.get("name").toString(), data.get("description").toString());
            return new ResponseEntity<>(data, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

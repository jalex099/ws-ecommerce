package com.javidev.ecommerce.controllers;

import com.javidev.ecommerce.entities.Product;
import com.javidev.ecommerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public ArrayList<Product> getProducts(
            @RequestParam(value = "name", required = false) String name ,
            @RequestParam(value = "category", required = false) Long category,
            @RequestParam(value = "is_active", required = false) Boolean isActive
    ) {
        return productService.getProducts(name, category, isActive);
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable("id") Long id) {
        return productService.getProduct(id);
    }

    @PostMapping
    public Product saveProduct(@RequestBody Product product) {
        return productService.saveProduct(product);
    }

}

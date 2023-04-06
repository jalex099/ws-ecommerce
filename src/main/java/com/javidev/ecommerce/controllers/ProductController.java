package com.javidev.ecommerce.controllers;

import com.javidev.ecommerce.entities.Product;
import com.javidev.ecommerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @RequestMapping("")
    public ArrayList<Product> getProducts(
            @RequestParam(value = "name", required = false) String name ,
            @RequestParam(value = "category", required = false) Long category,
            @RequestParam(value = "is_active", required = false) Boolean isActive
    ) {
        return productService.getProducts(name, category, isActive);
    }

}

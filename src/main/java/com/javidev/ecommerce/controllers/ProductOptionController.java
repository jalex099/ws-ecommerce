package com.javidev.ecommerce.controllers;

import com.javidev.ecommerce.services.ProductOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/api/product-options")
public class ProductOptionController {

    @Autowired
    private ProductOptionService productOptionService;

    @PostMapping
    public ResponseEntity<?> saveProductOption(@RequestBody HashMap<String, Object> data) {
        try{
            productOptionService.saveOption(
                    data.get("name").toString(),
                    data.get("description").toString(),
                    Long.parseLong(data.get("productId").toString())
            );
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new HashMap<String, String>() {{
                put("error", "Product option not created");
                put("message", e.getMessage());
            }}, HttpStatus.BAD_REQUEST);
        }
    }
}

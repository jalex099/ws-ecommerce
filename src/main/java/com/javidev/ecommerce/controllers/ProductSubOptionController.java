package com.javidev.ecommerce.controllers;

import com.javidev.ecommerce.services.ProductSubOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/api/product-suboptions")
public class ProductSubOptionController {
    @Autowired
    private ProductSubOptionService productSubOptionService;

    @PostMapping
    public ResponseEntity<?> saveSubOption(@RequestBody HashMap<String, Object> data) {
        try {
            productSubOptionService.saveOption(
                    Long.parseLong(data.get("optionId").toString()),
                    Double.parseDouble(data.get("aditionalPrice").toString()),
                    Long.parseLong(data.get("productOptionId").toString())
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

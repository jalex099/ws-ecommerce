package com.javidev.ecommerce.controllers;

import com.javidev.ecommerce.entities.Category;
import com.javidev.ecommerce.entities.Product;
import com.javidev.ecommerce.services.ProductService;
import com.javidev.ecommerce.services.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.Base64;
import java.util.HashMap;
import java.util.Objects;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final String UPLOAD_URI = "C:/uploads/";
    @Autowired
    private ProductService productService;

    @Autowired
    private S3Service s3Service;

    @GetMapping
    public ResponseEntity<?> getProducts(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "category", required = false) Long category,
            @RequestParam(value = "is_active", required = false) Boolean isActive
    ) {
        try {
            return new ResponseEntity<>(productService.getProducts(name, category, isActive), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(productService.getProduct(id), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping()
    public ResponseEntity<?> saveProduct(@RequestBody HashMap<String, Object> data) {
        try {
            productService.saveProduct(
                    data.get("name").toString(),
                    data.get("description").toString(),
                    Double.parseDouble(data.get("price").toString()),
                    Long.parseLong(data.get("category_id").toString())
            );
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping()
    public ResponseEntity<?> updateProduct(@RequestBody Product product) {
        try {
            Product updated =  productService.updateProduct(product);
            return new ResponseEntity<>(updated,HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") Long id) {
        try {
            Product deleted = productService.deleteProduct(id);
            return new ResponseEntity<>(deleted, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}

package com.javidev.ecommerce.controllers;

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
            return new ResponseEntity<>(new HashMap<String, String>() {{
                put("error", "Products not found");
            }}, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(productService.getProduct(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new HashMap<String, String>() {{
                put("error", "Product not found");
            }}, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping()
    public ResponseEntity<?> saveProduct(@RequestParam("image") MultipartFile image) {
        try {
            String imageName = image.getOriginalFilename();
            String imagePath = UPLOAD_URI + imageName;
            image.transferTo(Path.of(imagePath));
            HashMap<String, Object> imageData = s3Service.putObject(image);
            return new ResponseEntity<>(imageData, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new HashMap<String, String>() {{
                put("error", "Product not saved");
            }}, HttpStatus.BAD_REQUEST);
        }
    }

}

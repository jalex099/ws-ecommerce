package com.javidev.ecommerce.services;

import com.javidev.ecommerce.config.Params;
import com.javidev.ecommerce.entities.Product;
import com.javidev.ecommerce.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public ArrayList<Product> getProducts(String name, Long category, Boolean isActive) {
        return productRepository.findByFilters(name, category, isActive);
    }

    public Product getProduct(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public void saveProduct(String name, String description, Double price, Long category ) {
        productRepository.save(name, description, price, category, Long.parseLong(Params.COMPANY_ID));
    }

}

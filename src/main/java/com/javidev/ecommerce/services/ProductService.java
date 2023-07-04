package com.javidev.ecommerce.services;

import com.javidev.ecommerce.config.Params;
import com.javidev.ecommerce.entities.Product;
import com.javidev.ecommerce.repositories.ProductRepository;
import com.javidev.ecommerce.utils.Objects;
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

    public Product updateProduct(Product product) {
        Product oldProduct = productRepository.findById(product.getId()).orElse(null);
        if (oldProduct == null) return null;
        Product mergedProduct = Objects.merge(product, oldProduct);
        productRepository.save(mergedProduct);
        return mergedProduct;
    }

    public Product deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) return null;
        product.setIsActive(false);
        productRepository.save(product);
        return product;
    }

}

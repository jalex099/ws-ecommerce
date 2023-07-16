package com.javidev.ecommerce.services;

import com.javidev.ecommerce.config.Params;
import com.javidev.ecommerce.entities.Product;
import com.javidev.ecommerce.entities.ProductOption;
import com.javidev.ecommerce.entities.ProductSubOption;
import com.javidev.ecommerce.repositories.ProductRepository;
import com.javidev.ecommerce.utils.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    public Map<String, Object> saveProduct(Product product) {
        try{
            if(product.getId() != null) {
                Product productDB = productRepository.findById(product.getId()).orElse(null);
                if(productDB == null) {product.setId(null);}
                else { product.setCompanyId(productDB.getCompanyId()); }
            }
            if(product.getId() == null ){product.setIsActive(true); product.setCompanyId(Long.parseLong(Params.COMPANY_ID));}
            List<ProductOption> options = product.getOptions();
            product.clearOptions();
            for(ProductOption option : options) {
                List<ProductSubOption> subOptions = option.getSubOptions();
                option.clearSubOptions();
                for(ProductSubOption subOption : subOptions) {
                    subOption.setProductOptionId(option);
                    option.addSubOption(subOption);
                }
                product.addOption(option);
            }
            Product savedProduct = productRepository.save(product);
            return Objects.mapper(savedProduct);
        }  catch (Exception e) {
            return null;
        }
    }

    public Product updateProduct(Product product) {
        Product oldProduct = productRepository.findById(product.getId()).orElse(null);
        if (oldProduct == null) return null;
        Product mergedProduct = Objects.merge(product, oldProduct);
        productRepository.save(mergedProduct);
        return mergedProduct;
    }
    //Soft delete
//    public Product deleteProduct(Long id) {
//        Product product = productRepository.findById(id).orElse(null);
//        if (product == null) return null;
//        product.setIsActive(false);
//        productRepository.save(product);
//        return product;
//    }

    //Hard delete
    public Product deleteProduct(Long id) {
        try{
            Product product = productRepository.findById(id).orElse(null);
            if (product == null) return null;
            productRepository.delete(product);
            return product;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}

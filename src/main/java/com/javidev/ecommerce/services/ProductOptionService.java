package com.javidev.ecommerce.services;

import com.javidev.ecommerce.config.Params;
import com.javidev.ecommerce.entities.ProductOption;
import com.javidev.ecommerce.repositories.ProductOptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProductOptionService {
    @Autowired
    private ProductOptionRepository productOptionRepository;

    public ArrayList<ProductOption> getProductOptionsByProduct(Long productId) {
        return productOptionRepository.findByProductId(productId);
    }

    public void saveOption(String name, String description, Double aditionalPrice, Long productId) {

        productOptionRepository.save(name, description, aditionalPrice, productId, Long.parseLong(Params.COMPANY_ID));
    }

}

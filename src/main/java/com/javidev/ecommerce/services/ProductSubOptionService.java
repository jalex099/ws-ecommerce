package com.javidev.ecommerce.services;

import com.javidev.ecommerce.config.Params;
import com.javidev.ecommerce.entities.ProductOption;
import com.javidev.ecommerce.repositories.ProductOptionRepository;
import com.javidev.ecommerce.repositories.ProductSubOptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProductSubOptionService {
    @Autowired
    private ProductSubOptionRepository productSubOptionRepository;

    public void saveOption(String name, String description, Double aditionalPrice, Long productOptionId) {
        productSubOptionRepository.save(name, description, aditionalPrice, productOptionId, Long.parseLong(Params.COMPANY_ID));
    }
}

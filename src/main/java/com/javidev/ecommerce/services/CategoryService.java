package com.javidev.ecommerce.services;

import com.javidev.ecommerce.entities.Category;
import com.javidev.ecommerce.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public ArrayList<Category> getCategories(
            String name,
            Boolean isActive
    ) {
        return categoryRepository.findByFilters(name, isActive);
    }
}

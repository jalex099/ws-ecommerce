package com.javidev.ecommerce.services;

import com.javidev.ecommerce.config.Params;
import com.javidev.ecommerce.entities.Category;
import com.javidev.ecommerce.repositories.CategoryRepository;
import com.javidev.ecommerce.utils.Objects;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

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

    public void createCategory(String name , String description) {
        categoryRepository.save(name, description, Long.parseLong(Params.COMPANY_ID));
    }

    public Category updateCategory(Category category) {
        Category oldCategory = categoryRepository.findById(category.getId()).orElse(null);
        Category mergedCategory = Objects.merge(category, oldCategory);
        return categoryRepository.save(mergedCategory);
    }

    public Category deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category != null) {
            category.setIsActive(false);
            categoryRepository.save(category);
        }
        return category;
    }
}

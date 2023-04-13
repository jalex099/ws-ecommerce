package com.javidev.ecommerce.controllers;

import com.javidev.ecommerce.entities.Category;
import com.javidev.ecommerce.entities.Company;
import com.javidev.ecommerce.entities.Image;
import com.javidev.ecommerce.entities.Product;
import com.javidev.ecommerce.services.CategoryService;
import com.javidev.ecommerce.services.CompanyService;
import com.javidev.ecommerce.services.ImageService;
import com.javidev.ecommerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/api")
public class MainController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private CompanyService companyService;


    @GetMapping("/getData")
    public HashMap<String, Object> getData() {
        // GET THE CATEGORIES
        ArrayList<Category> categories = categoryService.getCategories(null, true);
        // GET THE PRODUCTS
        ArrayList<Product> products = productService.getProducts(null, null, true);
        // GET THE COMPANY INFORMATION
        Company company = companyService.getCompany();
        // GET THE IMAGES
        ArrayList<Image> images = imageService.getImages(null, null);
        HashMap<String, Object> data = new HashMap<>();
        data.put("categories", categories);
        data.put("products", products);
        data.put("images", images);
        data.put("company", company);
        return data;
    }
}

package com.javidev.ecommerce.services;

import com.javidev.ecommerce.config.Params;
import com.javidev.ecommerce.entities.Company;
import com.javidev.ecommerce.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    public Company getCompany() {
        return companyRepository.findById(Long.parseLong(Params.COMPANY_ID) ).orElse(null);
    }

}

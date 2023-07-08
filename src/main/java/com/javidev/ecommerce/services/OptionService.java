package com.javidev.ecommerce.services;

import com.javidev.ecommerce.config.Params;
import com.javidev.ecommerce.entities.Option;
import com.javidev.ecommerce.repositories.OptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class OptionService {
    @Autowired
    private OptionRepository optionRepository;

    public ArrayList<Option> getAll() {
        return (ArrayList<Option>) optionRepository.findAll();
    }

    public Option save(Option option) {
        option.setCompanyId(Long.parseLong(Params.COMPANY_ID));
        option.setIsActive(true);
        return optionRepository.save(option);
    }
}

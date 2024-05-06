package com.naukma.shopspringboot.subcategory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubcategoryService {
    private final SubcategoryRepo subcategoryRepo;

    @Autowired
    public SubcategoryService(SubcategoryRepo subcategoryRepo) {
        this.subcategoryRepo = subcategoryRepo;
    }
}

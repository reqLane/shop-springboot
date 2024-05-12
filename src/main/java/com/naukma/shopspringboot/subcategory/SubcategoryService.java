package com.naukma.shopspringboot.subcategory;

import com.naukma.shopspringboot.subcategory.model.Subcategory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SubcategoryService {
    private final SubcategoryRepo subcategoryRepo;

    public SubcategoryService(SubcategoryRepo subcategoryRepo) {
        this.subcategoryRepo = subcategoryRepo;
    }

    // BUSINESS LOGIC



    // CRUD OPERATIONS

    public Set<Subcategory> findAll() {
        Set<Subcategory> result = new HashSet<>();
        for (Subcategory subcategory : subcategoryRepo.findAll()) {
            result.add(subcategory);
        }
        return result;
    }

    public Optional<Subcategory> findById(Long id) {
        return subcategoryRepo.findById(id);
    }

    public Subcategory save(Subcategory subcategory) {
        return subcategoryRepo.save(subcategory);
    }

    public void deleteById(Long id) {
        subcategoryRepo.deleteById(id);
    }

    public void delete(Subcategory subcategory) {
        subcategoryRepo.deleteById(subcategory.getSubcategoryId());
    }

    public void deleteAll() {
        subcategoryRepo.deleteAll();
    }
}

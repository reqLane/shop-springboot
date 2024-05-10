package com.naukma.shopspringboot.subcategory;

import com.naukma.shopspringboot.subcategory.model.Subcategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SubcategoryService {
    private final SubcategoryRepo subcategoryRepo;

    @Autowired
    public SubcategoryService(SubcategoryRepo subcategoryRepo) {
        this.subcategoryRepo = subcategoryRepo;
    }

    // BUSINESS LOGIC



    // CRUD OPERATIONS

    public Subcategory getSubcategoryEntityByName(String name) {
        return subcategoryRepo.getSubcategoryByNameEqualsIgnoreCase(name);
    }

    private Set<Subcategory> findAll() {
        Set<Subcategory> result = new HashSet<>();
        for (Subcategory subcategory : subcategoryRepo.findAll()) {
            result.add(subcategory);
        }
        return result;
    }

    private Subcategory findById(Long id) {
        Optional<Subcategory> result = subcategoryRepo.findById(id);
        if(result.isEmpty()) return null;
        else return result.get();
    }

    private Subcategory create(Subcategory subcategory) {
        return subcategoryRepo.save(subcategory);
    }

    private void update(Subcategory subcategory) {
        subcategoryRepo.save(subcategory);
    }

    private void deleteById(Long id) {
        subcategoryRepo.deleteById(id);
    }

    private void delete(Subcategory subcategory) {
        subcategoryRepo.deleteById(subcategory.getSubcategoryId());
    }

    private void deleteAll() {
        subcategoryRepo.deleteAll();
    }
}

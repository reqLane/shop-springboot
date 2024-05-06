package com.naukma.shopspringboot.subcategory;

import com.naukma.shopspringboot.subcategory.model.Subcategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SubcategoryService {
    private final SubcategoryRepo subcategoryRepo;

    @Autowired
    public SubcategoryService(SubcategoryRepo subcategoryRepo) {
        this.subcategoryRepo = subcategoryRepo;
    }

    // BUSINESS LOGIC



    // CRUD OPERATIONS

    public List<Subcategory> findAll() {
        List<Subcategory> result = new ArrayList<>();
        for (Subcategory subcategory : subcategoryRepo.findAll()) {
            result.add(subcategory);
        }
        return result;
    }

    public Subcategory findById(Long id) {
        Optional<Subcategory> result = subcategoryRepo.findById(id);
        if(result.isEmpty()) return null;
        else return result.get();
    }

    public Subcategory create(Subcategory subcategory) {
        return subcategoryRepo.save(subcategory);
    }

    public void update(Subcategory subcategory) {
        subcategoryRepo.save(subcategory);
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

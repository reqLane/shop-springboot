package com.naukma.shopspringboot.category;

import com.naukma.shopspringboot.category.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepo categoryRepo;

    @Autowired
    public CategoryService(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    // BUSINESS LOGIC



    // CRUD OPERATIONS

    public List<Category> findAll() {
        List<Category> result = new ArrayList<>();
        for (Category category : categoryRepo.findAll()) {
            result.add(category);
        }
        return result;
    }

    public Category findById(Long id) {
        Optional<Category> result = categoryRepo.findById(id);
        if(result.isEmpty()) return null;
        else return result.get();
    }

    public Category create(Category category) {
        return categoryRepo.save(category);
    }

    public void update(Category category) {
        categoryRepo.save(category);
    }

    public void deleteById(Long id) {
        categoryRepo.deleteById(id);
    }

    public void delete(Category category) {
        categoryRepo.deleteById(category.getCategoryId());
    }

    public void deleteAll() {
        categoryRepo.deleteAll();
    }
}

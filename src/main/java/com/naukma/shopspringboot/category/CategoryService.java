package com.naukma.shopspringboot.category;

import com.naukma.shopspringboot.category.model.Category;
import com.naukma.shopspringboot.category.model.CategoryDTO;
import com.naukma.shopspringboot.subcategory.model.Subcategory;
import com.naukma.shopspringboot.subcategory.model.SubcategoryDTO;
import com.naukma.shopspringboot.util.DTOMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryService {
    private final CategoryRepo categoryRepo;

    @Autowired
    public CategoryService(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    // BUSINESS LOGIC

    public byte[] getCategoryPicture(Long categoryId) {
        Category category = findById(categoryId);
        if (category == null) throw new EntityNotFoundException(String.format("CATEGORY ID-%d - NOT FOUND", categoryId));

        return category.getPicture();
    }

    public List<CategoryDTO> getAllCategories() {
        return findAll()
                .stream()
                .filter(category -> !category.getSubcategories().isEmpty())
                .sorted(Comparator.comparingLong(Category::getCategoryId))
                .map(DTOMapper::toDTO)
                .toList();
    }

    public List<SubcategoryDTO> getSubcategoriesByCategory(Long categoryId) {
        Category category = findById(categoryId);
        if (category == null) throw new EntityNotFoundException(String.format("CATEGORY ID-%d - NOT FOUND", categoryId));

        return category.getSubcategories()
                .stream()
                .sorted(Comparator.comparingLong(Subcategory::getSubcategoryId))
                .map(DTOMapper::toDTO)
                .toList();
    }

    public List<CategoryDTO> getTrendingCategories(Integer size) {
        List<CategoryDTO> trending = findAll()
                .stream()
                .sorted((c1, c2) -> Integer.compare(c2.trendingIndex(), c1.trendingIndex()))
                .map(DTOMapper::toDTO)
                .toList();

        return trending.subList(0, Math.min(size, trending.size()));
    }

    // CRUD OPERATIONS

    public Category getCategoryEntityByName(String name) {
        return categoryRepo.getCategoryByNameEqualsIgnoreCase(name);
    }

    private Set<Category> findAll() {
        Set<Category> result = new HashSet<>();
        for (Category category : categoryRepo.findAll()) {
            result.add(category);
        }
        return result;
    }

    private Category findById(Long id) {
        Optional<Category> result = categoryRepo.findById(id);
        if(result.isEmpty()) return null;
        else return result.get();
    }

    private Category create(Category category) {
        return categoryRepo.save(category);
    }

    private void update(Category category) {
        categoryRepo.save(category);
    }

    private void deleteById(Long id) {
        categoryRepo.deleteById(id);
    }

    private void delete(Category category) {
        categoryRepo.deleteById(category.getCategoryId());
    }

    private void deleteAll() {
        categoryRepo.deleteAll();
    }
}

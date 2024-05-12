package com.naukma.shopspringboot.category;

import com.naukma.shopspringboot.category.model.Category;
import com.naukma.shopspringboot.category.model.CategoryDTO;
import com.naukma.shopspringboot.subcategory.model.Subcategory;
import com.naukma.shopspringboot.subcategory.model.SubcategoryDTO;
import com.naukma.shopspringboot.util.DTOMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryService {
    private final CategoryRepo categoryRepo;

    public CategoryService(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    // BUSINESS LOGIC

    public byte[] getCategoryPicture(Long categoryId) {
        Category category = findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("CATEGORY ID-%d - NOT FOUND", categoryId)));

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
        Category category = findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("CATEGORY ID-%d - NOT FOUND", categoryId)));

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

    public Set<Category> findAll() {
        Set<Category> result = new HashSet<>();
        for (Category category : categoryRepo.findAll()) {
            result.add(category);
        }
        return result;
    }

    public Optional<Category> findById(Long id) {
        return categoryRepo.findById(id);
    }

    public Category save(Category category) {
        return categoryRepo.save(category);
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

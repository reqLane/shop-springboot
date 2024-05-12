package com.naukma.shopspringboot.category;

import com.naukma.shopspringboot.category.model.CategoryDTO;
import com.naukma.shopspringboot.subcategory.model.SubcategoryDTO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/{categoryId}/picture")
    public ResponseEntity<byte[]> getCategoryPicture(@PathVariable("categoryId") Long categoryId) {
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(categoryService.getCategoryPicture(categoryId));
    }

    @GetMapping("")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/{categoryId}/subcategories")
    public ResponseEntity<List<SubcategoryDTO>> getSubcategoriesByCategory(@PathVariable("categoryId") Long categoryId) {
        List<SubcategoryDTO> subcategoryDTOs = categoryService.getSubcategoriesByCategory(categoryId);
        if (subcategoryDTOs == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(subcategoryDTOs);
    }

    @GetMapping("/trending")
    public ResponseEntity<List<CategoryDTO>> getTrendingCategories(@RequestParam(defaultValue = "4") Integer size) {
        return ResponseEntity.ok(categoryService.getTrendingCategories(size));
    }
}

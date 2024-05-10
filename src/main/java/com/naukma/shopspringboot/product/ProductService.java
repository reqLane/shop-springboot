package com.naukma.shopspringboot.product;

import com.naukma.shopspringboot.category.CategoryService;
import com.naukma.shopspringboot.category.model.Category;
import com.naukma.shopspringboot.color.ColorService;
import com.naukma.shopspringboot.color.model.Color;
import com.naukma.shopspringboot.material.MaterialService;
import com.naukma.shopspringboot.material.model.Material;
import com.naukma.shopspringboot.picture.model.Picture;
import com.naukma.shopspringboot.product.model.FilteredProductsDTO;
import com.naukma.shopspringboot.product.model.Product;
import com.naukma.shopspringboot.product.model.ProductDTO;
import com.naukma.shopspringboot.subcategory.SubcategoryService;
import com.naukma.shopspringboot.subcategory.model.Subcategory;
import com.naukma.shopspringboot.util.DTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ProductService {
    private final ProductRepo productRepo;
    private final CategoryService categoryService;
    private final SubcategoryService subcategoryService;
    private final ColorService colorService;
    private final MaterialService materialService;

    @Autowired
    public ProductService(ProductRepo productRepo, CategoryService categoryService, SubcategoryService subcategoryService, ColorService colorService, MaterialService materialService) {
        this.productRepo = productRepo;
        this.categoryService = categoryService;
        this.subcategoryService = subcategoryService;
        this.colorService = colorService;
        this.materialService = materialService;
    }

    // BUSINESS LOGIC

    public byte[] getProductPicture(Long productId) {
        Product product = findById(productId);
        if (product == null) return null;
        for (Picture picture : product.getPictures()) {
            return picture.getPicture();
        }
        return null;
    }

    public List<ProductDTO> getTrendingProducts(Integer size) {
        List<ProductDTO> trending = findAll()
                .stream()
                .sorted((p1, p2) -> Integer.compare(p2.trendingIndex(), p1.trendingIndex()))
                .map(DTOMapper::toDTO)
                .toList();

        return trending.subList(0, Math.min(size, trending.size()));
    }

    public Integer getFilteredProductsCount(String categoryName,
                                            String subcategoryName,
                                            String search,
                                            BigDecimal priceMin,
                                            BigDecimal priceMax,
                                            String materialName,
                                            String colorName) {
        Category category = categoryService.getCategoryEntityByName(categoryName);
        Subcategory subcategory = subcategoryService.getSubcategoryEntityByName(subcategoryName);
        Material material = materialService.getMaterialEntityByName(materialName);
        Color color = colorService.getColorEntityByName(colorName);

        List<Product> filtered = findAll()
                .stream()
                .filter(p -> p.passesFilters(category, subcategory, search, priceMin, priceMax, material, color))
                .toList();

        return filtered.size();
    }

    public FilteredProductsDTO getFilteredProducts(String categoryName,
                                                   String subcategoryName,
                                                   String search,
                                                   BigDecimal priceMin,
                                                   BigDecimal priceMax,
                                                   String materialName,
                                                   String colorName,
                                                   String priceSort,
                                                   Integer page,
                                                   Integer size) {
        if (page < 1) page = 1;
        if (size < 1) size = 3;

        Category category = categoryService.getCategoryEntityByName(categoryName);
        Subcategory subcategory = subcategoryService.getSubcategoryEntityByName(subcategoryName);
        Material material = materialService.getMaterialEntityByName(materialName);
        Color color = colorService.getColorEntityByName(colorName);

        List<Product> filtered = findAll()
                .stream()
                .filter(p -> p.passesFilters(category, subcategory, search, priceMin, priceMax, material, color))
                .sorted((o1, o2) -> priceSort.equalsIgnoreCase("desc") ? o2.getPrice().compareTo(o1.getPrice()) : o1.getPrice().compareTo(o2.getPrice()))
                .toList();

        int start = size * (page - 1);
        if (start >= filtered.size()) start = filtered.size() - size;
        List<ProductDTO> products = filtered.subList(start, Math.min(start + size, filtered.size()))
                .stream()
                .map(DTOMapper::toDTO)
                .toList();

        Set<Color> colors = filtered
                .stream()
                .map(Product::getColors)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());

        Set<Material> materials = filtered
                .stream()
                .map(Product::getMaterials)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());

        Stream<BigDecimal> pricesStream = filtered
                .stream()
                .map(Product::getPrice);

        BigDecimal priceLowBD = pricesStream
                .min(BigDecimal::compareTo)
                .orElse(null);
        BigDecimal priceHighBD = pricesStream
                .max(BigDecimal::compareTo)
                .orElse(null);

        Integer priceLow = priceLowBD != null ? (int)(priceLowBD.doubleValue()) : null;
        Integer priceHigh = priceHighBD != null ? ((int)(priceHighBD.doubleValue()) + 1) : null;

        return new FilteredProductsDTO(
                products,
                colors.stream().map(DTOMapper::toDTO).toList(),
                materials.stream().map(DTOMapper::toDTO).toList(),
                priceLow,
                priceHigh);
    }

    // CRUD OPERATIONS

    private Set<Product> findAll() {
        Set<Product> result = new HashSet<>();
        for (Product product : productRepo.findAll()) {
            result.add(product);
        }
        return result;
    }

    private Product findById(Long id) {
        Optional<Product> result = productRepo.findById(id);
        if(result.isEmpty()) return null;
        else return result.get();
    }

    private Product create(Product product) {
        return productRepo.save(product);
    }

    private void update(Product product) {
        productRepo.save(product);
    }

    private void deleteById(Long id) {
        productRepo.deleteById(id);
    }

    private void delete(Product product) {
        productRepo.deleteById(product.getProductId());
    }

    private void deleteAll() {
        productRepo.deleteAll();
    }
}

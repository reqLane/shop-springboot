package com.naukma.shopspringboot.product;

import com.naukma.shopspringboot.category.CategoryService;
import com.naukma.shopspringboot.category.model.Category;
import com.naukma.shopspringboot.color.ColorService;
import com.naukma.shopspringboot.color.model.Color;
import com.naukma.shopspringboot.material.MaterialService;
import com.naukma.shopspringboot.material.model.Material;
import com.naukma.shopspringboot.picture.model.Picture;
import com.naukma.shopspringboot.product.model.*;
import com.naukma.shopspringboot.subcategory.SubcategoryService;
import com.naukma.shopspringboot.subcategory.model.Subcategory;
import com.naukma.shopspringboot.util.DTOMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

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

    public List<byte[]> getProductPictures(Long productId) {
        Product product = findById(productId);
        if (product == null) throw new EntityNotFoundException(String.format("PRODUCT ID-%d - NOT FOUND", productId));

        return product.getPictures()
                .stream()
                .sorted(Comparator.comparingLong(Picture::getPictureId))
                .map(Picture::getPicture)
                .toList();
    }

    public List<ProductDTO> getTrendingProducts(Integer size) {
        List<ProductDTO> trending = findAll()
                .stream()
                .sorted((p1, p2) -> Integer.compare(p2.trendingIndex(), p1.trendingIndex()))
                .map(DTOMapper::toDTO)
                .toList();

        return trending.subList(0, Math.min(size, trending.size()));
    }

    public Integer getFilteredProductsCount(FilteredProductsRequest request) {
        Category category = categoryService.getCategoryEntityByName(request.categoryName());
        Subcategory subcategory = subcategoryService.getSubcategoryEntityByName(request.subcategoryName());

        Set<Material> materials = new HashSet<>();
        if (request.materials() != null) {
            materials = request.materials()
                    .stream()
                    .map(materialService::getMaterialEntityByName)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
        }
        Set<Color> colors = new HashSet<>();
        if(request.colors() != null) {
            colors = request.colors()
                    .stream()
                    .map(colorService::getColorEntityByName)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
        }

        Set<Material> finalMaterials = materials;
        Set<Color> finalColors = colors;
        List<Product> filtered = findAll()
                .stream()
                .filter(p -> p.passesFilters(category, subcategory, request.search(), request.priceMin(), request.priceMax(), finalMaterials, finalColors))
                .toList();

        return filtered.size();
    }

    public EdgePricesDTO getEdgePrices(FilteredProductsRequest request) {
        Category category = categoryService.getCategoryEntityByName(request.categoryName());
        Subcategory subcategory = subcategoryService.getSubcategoryEntityByName(request.subcategoryName());

        List<Product> selection = findAll()
                .stream()
                .filter(p -> subcategory == null || p.getSubcategory() == subcategory)
                .filter(p -> category == null || p.getSubcategory().getCategory() == category)
                .toList();

        BigDecimal priceLowBD = selection
                .stream()
                .map(Product::getPrice)
                .min(BigDecimal::compareTo)
                .orElse(null);
        BigDecimal priceHighBD = selection
                .stream()
                .map(Product::getPrice)
                .max(BigDecimal::compareTo)
                .orElse(null);

        Integer priceLow = priceLowBD != null ? (int) priceLowBD.doubleValue() : 0;
        Integer priceHigh = priceHighBD != null ? (int)priceHighBD.doubleValue() + 1 : 25000;

        return new EdgePricesDTO(priceLow, priceHigh);
    }

    public List<ProductDTO> getFilteredProducts(FilteredProductsRequest request,
                                                String priceSort,
                                                Integer page,
                                                Integer size) {
        if (page < 1) page = 1;
        if (size < 1) size = 3;

        Category category = categoryService.getCategoryEntityByName(request.categoryName());
        Subcategory subcategory = subcategoryService.getSubcategoryEntityByName(request.subcategoryName());

        Set<Material> materials = new HashSet<>();
        if (request.materials() != null) {
            materials = request.materials()
                    .stream()
                    .map(materialService::getMaterialEntityByName)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
        }
        Set<Color> colors = new HashSet<>();
        if(request.colors() != null) {
            colors = request.colors()
                    .stream()
                    .map(colorService::getColorEntityByName)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
        }

        Set<Material> finalMaterials = materials;
        Set<Color> finalColors = colors;
        List<Product> filtered = findAll()
                .stream()
                .filter(p -> p.passesFilters(category, subcategory, request.search(), request.priceMin(), request.priceMax(), finalMaterials, finalColors))
                .sorted((o1, o2) -> {
                    int priceComparison = priceSort.equalsIgnoreCase("desc") ?
                            o2.getPrice().compareTo(o1.getPrice()) :
                            o1.getPrice().compareTo(o2.getPrice());
                    if (priceComparison == 0) {
                        return o1.getProductId().compareTo(o2.getProductId());
                    }
                    return priceComparison;
                })
                .toList();

        int start = size * (page - 1);
        if (start >= filtered.size()) start = Math.max(0, filtered.size() - size);
        List<ProductDTO> products = filtered.subList(start, Math.min(start + size, filtered.size()))
                .stream()
                .map(DTOMapper::toDTO)
                .toList();

        return products;
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

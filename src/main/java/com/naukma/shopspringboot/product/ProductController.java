package com.naukma.shopspringboot.product;

import com.naukma.shopspringboot.product.model.FilteredProductsDTO;
import com.naukma.shopspringboot.product.model.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{productId}/picture")
    public ResponseEntity<byte[]> getProductPicture(@PathVariable("productId") Long productId) {
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(productService.getProductPicture(productId));
    }

    @GetMapping("/trending")
    public ResponseEntity<List<ProductDTO>> getTrendingProducts(@RequestParam(name = "size", defaultValue = "4") Integer size) {
        return ResponseEntity.ok(productService.getTrendingProducts(size));
    }

    @GetMapping("/filtered-count")
    public ResponseEntity<Integer> getFilteredProductsCount(
            @RequestParam(name = "cat", required = false) String category,
            @RequestParam(name = "subcat", required = false) String subcategory,
            @RequestParam(name = "search", required = false) String search,
            @RequestParam(name = "priceMin", required = false) BigDecimal priceMin,
            @RequestParam(name = "priceMax", required = false) BigDecimal priceMax,
            @RequestParam(name = "material", required = false) String material,
            @RequestParam(name = "color", required = false) String color) {
        return ResponseEntity.ok(productService.getFilteredProductsCount(category, subcategory, search, priceMin, priceMax, material, color));
    }

    @GetMapping("/filtered")
    public ResponseEntity<FilteredProductsDTO> getFilteredProductsData(
            @RequestParam(name = "cat", required = false) String category,
            @RequestParam(name = "subcat", required = false) String subcategory,
            @RequestParam(name = "search", required = false) String search,
            @RequestParam(name = "priceMin", required = false) BigDecimal priceMin,
            @RequestParam(name = "priceMax", required = false) BigDecimal priceMax,
            @RequestParam(name = "material", required = false) String material,
            @RequestParam(name = "color", required = false) String color,
            @RequestParam(name = "priceSort", defaultValue = "asc") String priceSort,
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "12") Integer size) {
        return ResponseEntity.ok(productService.getFilteredProducts(category, subcategory, search, priceMin, priceMax, material, color, priceSort, page, size));
    }
}

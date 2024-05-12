package com.naukma.shopspringboot.product;

import com.naukma.shopspringboot.color.model.ColorDTO;
import com.naukma.shopspringboot.material.model.MaterialDTO;
import com.naukma.shopspringboot.product.model.EdgePricesDTO;
import com.naukma.shopspringboot.product.model.FilteredProductsRequestDTO;
import com.naukma.shopspringboot.product.model.ProductDTO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{productId}/pictures")
    public ResponseEntity<List<byte[]>> getProductPicture(@PathVariable("productId") Long productId) {
        return ResponseEntity.ok(productService.getProductPictures(productId));
    }

    @GetMapping("/{productId}/picture-main")
    public ResponseEntity<byte[]> getProductMainPicture(@PathVariable("productId") Long productId) {
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(productService.getProductPictures(productId).get(0));
    }

    @GetMapping("/{productId}/colors")
    public ResponseEntity<List<ColorDTO>> getProductColors(@PathVariable("productId") Long productId) {
        return ResponseEntity.ok(productService.getProductColors(productId));
    }

    @GetMapping("/{productId}/materials")
    public ResponseEntity<List<MaterialDTO>> getProductMaterials(@PathVariable("productId") Long productId) {
        return ResponseEntity.ok(productService.getProductMaterials(productId));
    }

    @GetMapping("/trending")
    public ResponseEntity<List<ProductDTO>> getTrendingProducts(@RequestParam(name = "size", defaultValue = "4") Integer size) {
        return ResponseEntity.ok(productService.getTrendingProducts(size));
    }

    @PostMapping("/filtered-count")
    public ResponseEntity<Integer> getFilteredProductsCount(@RequestBody FilteredProductsRequestDTO body) {
        return ResponseEntity.ok(productService.getFilteredProductsCount(body));
    }

    @PostMapping("/edge-prices")
    public ResponseEntity<EdgePricesDTO> getEdgePrices(@RequestBody FilteredProductsRequestDTO body) {
        return ResponseEntity.ok(productService.getEdgePrices(body));
    }

    @PostMapping("/filtered")
    public ResponseEntity<List<ProductDTO>> getFilteredProductsData(
            @RequestBody FilteredProductsRequestDTO body,
            @RequestParam(name = "price-sort", defaultValue = "asc") String priceSort,
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "12") Integer size) {
        return ResponseEntity.ok(productService.getFilteredProducts(body, priceSort, page, size));
    }

    @GetMapping("/{productId}/recommendations")
    public ResponseEntity<List<ProductDTO>> getProductRecommendations(
            @PathVariable("productId") Long productId,
            @RequestParam(name = "size", defaultValue = "4") Integer size) {
        return ResponseEntity.ok(productService.getProductRecommendations(productId, size));
    }
}

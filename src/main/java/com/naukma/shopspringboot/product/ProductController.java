package com.naukma.shopspringboot.product;

import com.naukma.shopspringboot.product.model.EdgePricesDTO;
import com.naukma.shopspringboot.product.model.FilteredProductsRequest;
import com.naukma.shopspringboot.product.model.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Integer> getFilteredProductsCount(@RequestBody FilteredProductsRequest body) {
        return ResponseEntity.ok(productService.getFilteredProductsCount(body));
    }

    @GetMapping("/edge-prices")
    public ResponseEntity<EdgePricesDTO> getEdgePrices(@RequestBody FilteredProductsRequest body) {
        return ResponseEntity.ok(productService.getEdgePrices(body));
    }

    @GetMapping("/filtered")
    public ResponseEntity<List<ProductDTO>> getFilteredProductsData(
            @RequestBody FilteredProductsRequest body,
            @RequestParam(name = "price-sort", defaultValue = "asc") String priceSort,
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "12") Integer size) {
        return ResponseEntity.ok(productService.getFilteredProducts(body, priceSort, page, size));
    }
}

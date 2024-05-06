package com.naukma.shopspringboot.product;

import com.naukma.shopspringboot.product.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepo productRepo;

    @Autowired
    public ProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    // BUSINESS LOGIC



    // CRUD OPERATIONS

    public List<Product> findAll() {
        List<Product> result = new ArrayList<>();
        for (Product product : productRepo.findAll()) {
            result.add(product);
        }
        return result;
    }

    public Product findById(Long id) {
        Optional<Product> result = productRepo.findById(id);
        if(result.isEmpty()) return null;
        else return result.get();
    }

    public Product create(Product product) {
        return productRepo.save(product);
    }

    public void update(Product product) {
        productRepo.save(product);
    }

    public void deleteById(Long id) {
        productRepo.deleteById(id);
    }

    public void delete(Product product) {
        productRepo.deleteById(product.getProductId());
    }

    public void deleteAll() {
        productRepo.deleteAll();
    }
}

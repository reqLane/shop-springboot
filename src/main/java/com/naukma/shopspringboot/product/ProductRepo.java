package com.naukma.shopspringboot.product;

import com.naukma.shopspringboot.product.model.Product;
import com.naukma.shopspringboot.subcategory.model.Subcategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ProductRepo extends CrudRepository<Product, Long> {
    List<Product> findAllBySubcategoryIn(Set<Subcategory> subcategories);
}

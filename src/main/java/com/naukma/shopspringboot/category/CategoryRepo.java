package com.naukma.shopspringboot.category;

import com.naukma.shopspringboot.category.model.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends CrudRepository<Category, Long> {

}

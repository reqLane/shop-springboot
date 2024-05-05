package com.naukma.shopspringboot.subcategory;

import com.naukma.shopspringboot.subcategory.model.Subcategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubcategoryRepo extends CrudRepository<Subcategory, Long> {

}

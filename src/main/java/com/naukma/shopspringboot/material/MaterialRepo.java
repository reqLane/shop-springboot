package com.naukma.shopspringboot.material;

import com.naukma.shopspringboot.material.model.Material;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialRepo extends CrudRepository<Material, Long> {

}

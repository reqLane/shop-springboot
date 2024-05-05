package com.naukma.shopspringboot.color;

import com.naukma.shopspringboot.color.model.Color;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColorRepo extends CrudRepository<Color, Long> {

}

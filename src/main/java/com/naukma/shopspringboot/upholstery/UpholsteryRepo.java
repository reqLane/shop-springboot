package com.naukma.shopspringboot.upholstery;

import com.naukma.shopspringboot.upholstery.model.Upholstery;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UpholsteryRepo extends CrudRepository<Upholstery, Long> {

}

package com.naukma.shopspringboot.order;

import com.naukma.shopspringboot.order.model.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepo extends CrudRepository<Order, Long> {

}

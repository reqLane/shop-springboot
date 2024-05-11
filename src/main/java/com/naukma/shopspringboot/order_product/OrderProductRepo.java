package com.naukma.shopspringboot.order_product;

import com.naukma.shopspringboot.order_product.model.OrderProduct;
import com.naukma.shopspringboot.order_product.model.OrderProductId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderProductRepo extends CrudRepository<OrderProduct, OrderProductId> {

}

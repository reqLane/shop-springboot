package com.naukma.shopspringboot.order_product;

import com.naukma.shopspringboot.order_product.model.OrderProduct;
import com.naukma.shopspringboot.order_product.model.OrderProductId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderProductService {
    private final OrderProductRepo orderProductRepo;

    @Autowired
    public OrderProductService(OrderProductRepo orderProductRepo) {
        this.orderProductRepo = orderProductRepo;
    }

    // BUSINESS LOGIC

    //CRUD OPERATIONS

    public Set<OrderProduct> findAll() {
        Set<OrderProduct> result = new HashSet<>();
        for (OrderProduct orderProduct : orderProductRepo.findAll()) {
            result.add(orderProduct);
        }
        return result;
    }

    public Optional<OrderProduct> findById(OrderProductId id) {
        return orderProductRepo.findById(id);
    }

    public OrderProduct create(OrderProduct orderProduct) {
        return orderProductRepo.save(orderProduct);
    }

    public List<OrderProduct> createAll(Iterable<OrderProduct> orderProducts) {
        List<OrderProduct> result = new ArrayList<>();
        for (OrderProduct orderProduct : orderProductRepo.saveAll(orderProducts)) {
            result.add(orderProduct);
        }
        return result;
    }

    public void update(OrderProduct orderProduct) {
        orderProductRepo.save(orderProduct);
    }

    public void deleteById(OrderProductId id) {
        orderProductRepo.deleteById(id);
    }

    public void delete(OrderProduct orderProduct) {
        orderProductRepo.deleteById(orderProduct.getId());
    }

    public void deleteAll() {
        orderProductRepo.deleteAll();
    }
}

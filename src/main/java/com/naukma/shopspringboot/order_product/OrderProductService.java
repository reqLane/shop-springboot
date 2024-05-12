package com.naukma.shopspringboot.order_product;

import com.naukma.shopspringboot.order_product.model.OrderProduct;
import com.naukma.shopspringboot.order_product.model.OrderProductId;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderProductService {
    private final OrderProductRepo orderProductRepo;

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

    public OrderProduct save(OrderProduct orderProduct) {
        return orderProductRepo.save(orderProduct);
    }

    public List<OrderProduct> saveAll(Iterable<OrderProduct> orderProducts) {
        List<OrderProduct> result = new ArrayList<>();
        for (OrderProduct orderProduct : orderProductRepo.saveAll(orderProducts)) {
            result.add(orderProduct);
        }
        return result;
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

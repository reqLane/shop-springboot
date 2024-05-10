package com.naukma.shopspringboot.order;

import com.naukma.shopspringboot.order.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderService {
    private final OrderRepo orderRepo;

    @Autowired
    public OrderService(OrderRepo orderRepo) {
        this.orderRepo = orderRepo;
    }

    // BUSINESS LOGIC



    // CRUD OPERATIONS

    private Set<Order> findAll() {
        Set<Order> result = new HashSet<>();
        for (Order order : orderRepo.findAll()) {
            result.add(order);
        }
        return result;
    }

    private Order findById(Long id) {
        Optional<Order> result = orderRepo.findById(id);
        if(result.isEmpty()) return null;
        else return result.get();
    }

    private Order create(Order order) {
        return orderRepo.save(order);
    }

    private void update(Order order) {
        orderRepo.save(order);
    }

    private void deleteById(Long id) {
        orderRepo.deleteById(id);
    }

    private void delete(Order order) {
        orderRepo.deleteById(order.getOrderId());
    }

    private void deleteAll() {
        orderRepo.deleteAll();
    }
}

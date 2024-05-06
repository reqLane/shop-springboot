package com.naukma.shopspringboot.order;

import com.naukma.shopspringboot.order.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepo orderRepo;

    @Autowired
    public OrderService(OrderRepo orderRepo) {
        this.orderRepo = orderRepo;
    }

    // BUSINESS LOGIC



    // CRUD OPERATIONS

    public List<Order> findAll() {
        List<Order> result = new ArrayList<>();
        for (Order order : orderRepo.findAll()) {
            result.add(order);
        }
        return result;
    }

    public Order findById(Long id) {
        Optional<Order> result = orderRepo.findById(id);
        if(result.isEmpty()) return null;
        else return result.get();
    }

    public Order create(Order order) {
        return orderRepo.save(order);
    }

    public void update(Order order) {
        orderRepo.save(order);
    }

    public void deleteById(Long id) {
        orderRepo.deleteById(id);
    }

    public void delete(Order order) {
        orderRepo.deleteById(order.getOrderId());
    }

    public void deleteAll() {
        orderRepo.deleteAll();
    }
}

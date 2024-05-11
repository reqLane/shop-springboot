package com.naukma.shopspringboot.order;

import com.naukma.shopspringboot.order.model.OrderDTO;
import com.naukma.shopspringboot.order.model.OrderRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderRequestDTO body) {
        OrderDTO orderDTO = orderService.createOrder(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderDTO);
    }
}

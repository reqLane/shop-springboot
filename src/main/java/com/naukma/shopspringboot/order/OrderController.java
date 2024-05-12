package com.naukma.shopspringboot.order;

import com.naukma.shopspringboot.auth.AuthService;
import com.naukma.shopspringboot.order.model.OrderDTO;
import com.naukma.shopspringboot.order.model.OrderRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;
    private final AuthService authService;

    public OrderController(OrderService orderService, AuthService authService) {
        this.orderService = orderService;
        this.authService = authService;
    }

    @PostMapping("/create")
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderRequestDTO body) {
        if (!body.userId().equals(authService.getCurrentUserId()))
            throw new BadCredentialsException("UNAUTHORIZED - CREATING ORDER");

        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(body));
    }
}

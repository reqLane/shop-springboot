package com.naukma.shopspringboot.order;

import com.naukma.shopspringboot.color.ColorService;
import com.naukma.shopspringboot.color.model.Color;
import com.naukma.shopspringboot.exception.InvalidOrderDataException;
import com.naukma.shopspringboot.material.MaterialService;
import com.naukma.shopspringboot.material.model.Material;
import com.naukma.shopspringboot.order.model.Order;
import com.naukma.shopspringboot.order.model.OrderDTO;
import com.naukma.shopspringboot.order.model.OrderRequestDTO;
import com.naukma.shopspringboot.order_product.OrderProductService;
import com.naukma.shopspringboot.order_product.model.OrderProduct;
import com.naukma.shopspringboot.order_product.model.OrderProductRequestDTO;
import com.naukma.shopspringboot.product.ProductService;
import com.naukma.shopspringboot.product.model.Product;
import com.naukma.shopspringboot.user.UserService;
import com.naukma.shopspringboot.user.model.User;
import com.naukma.shopspringboot.util.DTOMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderService {
    private final OrderRepo orderRepo;
    private final ProductService productService;
    private final ColorService colorService;
    private final MaterialService materialService;
    private final OrderProductService orderProductService;
    private final UserService userService;

    public OrderService(OrderRepo orderRepo, ProductService productService, ColorService colorService, MaterialService materialService, OrderProductService orderProductService, UserService userService) {
        this.orderRepo = orderRepo;
        this.productService = productService;
        this.colorService = colorService;
        this.materialService = materialService;
        this.orderProductService = orderProductService;
        this.userService = userService;
    }

    // BUSINESS LOGIC

    public OrderDTO createOrder(OrderRequestDTO request) {
        if (request.orderProducts() == null || request.orderProducts().isEmpty())
            throw new InvalidOrderDataException("CAN'T CREATE EMPTY ORDER");
        if (request.price() == null)
            throw new InvalidOrderDataException("CAN'T CREATE ORDER WITHOUT PRICE");

        User user = userService.findById(request.userId())
                .orElseThrow(() -> new EntityNotFoundException(String.format("USER ID-%d NOT FOUND", request.userId())));

        Order order = new Order(user);
        for (OrderProductRequestDTO orderProductDTO : request.orderProducts()) {
            Product product = productService.findById(orderProductDTO.productId())
                    .orElseThrow(() -> new EntityNotFoundException(String.format("PRODUCT ID-%d NOT FOUND", orderProductDTO.productId())));
            Color color = colorService.findById(orderProductDTO.colorId())
                    .orElseThrow(() -> new EntityNotFoundException(String.format("COLOR ID-%d NOT FOUND", orderProductDTO.colorId())));
            Material material = materialService.findById(orderProductDTO.materialId())
                    .orElseThrow(() -> new EntityNotFoundException(String.format("MATERIAL ID-%d NOT FOUND", orderProductDTO.materialId())));

            if (!product.getColors().contains(color))
                throw new InvalidOrderDataException(String.format("PRODUCT ID-%d DOESN'T HAVE COLOR ID-%d", product.getProductId(), color.getColorId()));
            if (!product.getMaterials().contains(material))
                throw new InvalidOrderDataException(String.format("PRODUCT ID-%d DOESN'T HAVE MATERIAL ID-%d", product.getProductId(), material.getMaterialId()));

            OrderProduct orderProduct = new OrderProduct(orderProductDTO.amount(), order, product, color, material);
            order.getOrderProducts().add(orderProduct);
        }

        order.calculatePrice();
        if (!order.getPrice().equals(request.price()))
            throw new InvalidOrderDataException(String.format("RESULTING PRICE %.2f DOESN'T MATCH REQUEST PRICE %.2f", order.getPrice(), request.price()));

        save(order);
        orderProductService.saveAll(order.getOrderProducts());

        return DTOMapper.toDTO(order);
    }

    // CRUD OPERATIONS

    public Set<Order> findAll() {
        Set<Order> result = new HashSet<>();
        for (Order order : orderRepo.findAll()) {
            result.add(order);
        }
        return result;
    }

    public Optional<Order> findById(Long id) {
        return orderRepo.findById(id);
    }

    public Order save(Order order) {
        return orderRepo.save(order);
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

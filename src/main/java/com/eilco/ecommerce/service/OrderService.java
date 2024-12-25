package com.eilco.ecommerce.service;

import com.eilco.ecommerce.dto.*;
import com.eilco.ecommerce.model.entities.*;
import com.eilco.ecommerce.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    public OrderResponse createOrder(OrderRequest orderRequest) {
        // Find the user by ID
        User user = userRepository.findById(orderRequest.getUserId().intValue())
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + orderRequest.getUserId()));

        // Validate product availability
        List<OrderItem> orderItems = orderRequest.getItems().stream().map(item -> {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + item.getProductId()));

            if (product.getQuantity() < item.getQuantity()) {
                throw new IllegalArgumentException("Insufficient stock for product: " + product.getName());
            }

            return OrderItem.builder()
                    .product(product)
                    .quantity(item.getQuantity())
                    .price(product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                    .build();
        }).collect(Collectors.toList());

        // Calculate total amount
        BigDecimal totalAmount = orderItems.stream()
                .map(OrderItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Create and save the order
        Order order = Order.builder()
                .user(user)
                .orderItems(orderItems)
                .orderDate(LocalDateTime.now())
                .totalAmount(totalAmount)
                .build();

        orderItems.forEach(item -> item.setOrder(order));
        Order savedOrder = orderRepository.save(order);

        return convertOrderToResponse(savedOrder);
    }

    private OrderResponse convertOrderToResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getOrderId().intValue())
                .userId(order.getUser().getId())
                .orderDate(order.getOrderDate())
                .totalAmount(order.getTotalAmount())
                .items(order.getOrderItems().stream().map(this::convertOrderItemToResponse).collect(Collectors.toList()))
                .build();
    }

    private OrderItemResponse convertOrderItemToResponse(OrderItem item) {
        return OrderItemResponse.builder()
                .productId(item.getProduct().getId())
                .productName(item.getProduct().getName())
                .quantity(item.getQuantity())
                .price(item.getPrice())
                .build();
    }
}

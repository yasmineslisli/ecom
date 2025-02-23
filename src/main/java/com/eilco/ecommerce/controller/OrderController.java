package com.eilco.ecommerce.controller;

import com.eilco.ecommerce.dto.*;
import com.eilco.ecommerce.model.entities.Order;
import com.eilco.ecommerce.model.entities.OrderStatus;
import com.eilco.ecommerce.model.entities.Product;
import com.eilco.ecommerce.service.AuthService;
import com.eilco.ecommerce.service.OrderService;
import com.eilco.ecommerce.service.PaymentDetailsService;
import com.eilco.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final ProductService productService;
    private final AuthService userService;
    private final PaymentDetailsService paymentDetailsService;

    @GetMapping("/create")
    public String showOrderForm(@RequestParam(value = "productId", required = false) Long productId, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Long userId = userService.getUserIdByUsername(username);

        List<Product> products = productService.findAll();
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setUserId(userId);

        if (productId != null) {
            OrderItemRequest item = new OrderItemRequest();
            item.setProductId(productId);
            item.setQuantity(1);
            orderRequest.setItems(List.of(item));
        }
        String role = authentication.getAuthorities().stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse(null);
        model.addAttribute("role", role);

        model.addAttribute("orderRequest", orderRequest);
        model.addAttribute("products", products);
        model.addAttribute("userId", userId);

        return "order-form";
    }

    @PostMapping("/create")
    public String createOrder(@ModelAttribute OrderRequest orderRequest, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String role = authentication.getAuthorities().stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse(null);
        model.addAttribute("role", role);
        String username = authentication.getName();
        Long userId = userService.getUserIdByUsername(username);

        orderRequest.setUserId(userId);

        OrderResponse orderResponse = orderService.createOrder(orderRequest);
        model.addAttribute("orderResponse", orderResponse);

        return "order-success";
    }


    @GetMapping("/my-orders")
    public String viewUserOrders(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String role = authentication.getAuthorities().stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse(null);
        model.addAttribute("role", role);
        String username = authentication.getName();
        Long userId = userService.getUserIdByUsername(username);

        List<OrderResponse> userOrders = orderService.getOrdersByUserId(userId);
        model.addAttribute("orders", userOrders);

        return "user-orders";
    }

    @GetMapping("/all-orders")
    public String viewAllOrders(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String role = authentication.getAuthorities().stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse(null);
        model.addAttribute("role", role);
        List<OrderResponse> allOrders = orderService.getAllOrders();
        model.addAttribute("orders", allOrders);

        return "all-orders";
    }

    @PostMapping("/validate/{orderId}")
    public String validateOrder(@PathVariable Long orderId) {
        orderService.validateOrder(orderId);
        return "redirect:/orders/all-orders";
    }

    @GetMapping("/paymentform")
    public String showPaymentPage(@RequestParam("orderId") Long orderId, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String role = authentication.getAuthorities().stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse(null);
        model.addAttribute("role", role);
        Order order = orderService.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + orderId));

        model.addAttribute("order", order);
        model.addAttribute("paymentDetailsRequest", new PaymentDetailsRequest());

        return "payment-form";
    }


    @PostMapping("/payment")
    public String processPayment(@ModelAttribute PaymentDetailsRequest paymentDetailsRequest) {
        paymentDetailsRequest.setPaymentDate(LocalDateTime.now());
        paymentDetailsRequest.setPaymentStatus(String.valueOf(OrderStatus.PAID));
        paymentDetailsService.save(paymentDetailsRequest);
        return "redirect:/orders/success/" + paymentDetailsRequest.getOrderId();
    }
    @GetMapping("/success/{orderId}")
    public String showPaymentSuccessPage(@PathVariable Long orderId, Model model) {
        Order order = orderService.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + orderId));

        model.addAttribute("order", order);
        return "payment-success";
    }




}

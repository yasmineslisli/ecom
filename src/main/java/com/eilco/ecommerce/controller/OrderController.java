package com.eilco.ecommerce.controller;

import com.eilco.ecommerce.dto.OrderRequest;
import com.eilco.ecommerce.dto.OrderItemRequest;
import com.eilco.ecommerce.model.entities.Product;
import com.eilco.ecommerce.service.AuthService;
import com.eilco.ecommerce.service.OrderService;
import com.eilco.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final ProductService productService;
    private final AuthService userService;

    @GetMapping("/create")
    public String showOrderForm(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Long userId = userService.getUserIdByUsername(username);

        List<Product> products = productService.findAll();

        model.addAttribute("orderRequest", new OrderRequest());
        model.addAttribute("products", products);
        model.addAttribute("userId", userId);

        return "order-form";
    }


    @PostMapping("/create")
    public String createOrder(@ModelAttribute OrderRequest orderRequest, Model model) {
        model.addAttribute("orderResponse", orderService.createOrder(orderRequest));
        return "order-success";
    }

}

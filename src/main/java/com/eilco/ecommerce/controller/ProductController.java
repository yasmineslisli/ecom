package com.eilco.ecommerce.controller;

import com.eilco.ecommerce.dto.ProductRequest;
import com.eilco.ecommerce.dto.ProductResponse;
import org.springframework.security.core.GrantedAuthority;
import com.eilco.ecommerce.service.CategoryService;
import com.eilco.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping("/add")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new ProductRequest());
        model.addAttribute("categories", categoryService.findAll()); // Pass categories to the form
        return "add-product";
    }

    @PostMapping("/add")
    public String addProduct(@ModelAttribute("product") ProductRequest productRequest) {
        productService.save(productRequest);
        return "redirect:/products/product-list1";
    }

    @GetMapping("/product-list1")
    public String showProducts1(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String role = authentication.getAuthorities().stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse(null);

        // Get all products and group them by category
        Map<String, List<ProductResponse>> productsByCategory = productService.findAll().stream()
                .map(productService::convertProductToResponse)
                .collect(Collectors.groupingBy(ProductResponse::getCategoryName));

        model.addAttribute("productsByCategory", productsByCategory);
        model.addAttribute("role", role);

        return "product-list1";
    }



    @GetMapping("/{id}/edit")
    public String showEditProductForm(@PathVariable("id") Long id, Model model) {
        Optional<ProductResponse> productResponse = productService.findById(id)
                .map(productService::convertProductToResponse);

        if (productResponse.isPresent()) {
            model.addAttribute("product", productResponse.get());
            model.addAttribute("categories", categoryService.findAll());
            return "edit-product";
        }
        return "product-list1";
    }

    @PostMapping("/{id}/update")
    public String updateProduct(@PathVariable("id") Long id,
                                @ModelAttribute ProductRequest productRequest) {
        productRequest.setId(id);
        productService.update(productRequest, id);
        return "redirect:/products/product-list1";
    }

    @GetMapping("/{id}/delete")
    public String deleteProduct(@PathVariable("id") Long id) {
        productService.deleteById(id);
        return "redirect:/products/product-list1";
    }
}

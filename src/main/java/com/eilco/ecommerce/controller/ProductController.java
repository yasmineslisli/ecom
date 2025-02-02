package com.eilco.ecommerce.controller;

import com.eilco.ecommerce.dto.ProductRequest;
import com.eilco.ecommerce.dto.ProductResponse;
import com.eilco.ecommerce.model.entities.Category;
import com.eilco.ecommerce.service.CategoryService;
import com.eilco.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
        return "redirect:/products/product-list";
    }

    @GetMapping("/product-list")
    public String showProducts(Model model) {
        List<ProductResponse> products = productService.findAll().stream()
                .map(productService::convertProductToResponse)
                .toList();
        model.addAttribute("products", products);
        return "product-list";
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
        return "redirect:/products/product-list";
    }

    @PostMapping("/{id}/update")
    public String updateProduct(@PathVariable("id") Long id,
                                @ModelAttribute ProductRequest productRequest) {
        productRequest.setId(id);
        productService.update(productRequest, id);
        return "redirect:/products/product-list";
    }

    @GetMapping("/{id}/delete")
    public String deleteProduct(@PathVariable("id") Long id) {
        productService.deleteById(id);
        return "redirect:/products/product-list";
    }
}

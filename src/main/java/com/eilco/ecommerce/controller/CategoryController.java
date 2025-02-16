package com.eilco.ecommerce.controller;

import com.eilco.ecommerce.dto.CategoryRequest;
import com.eilco.ecommerce.model.entities.Category;
import com.eilco.ecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/add")
    public String showAddCategoryForm(Model model) {
        model.addAttribute("category", new CategoryRequest());
        return "add-category";
    }

    @PostMapping("/add")
    public String addCategory(@ModelAttribute("category") CategoryRequest categoryRequest) {
        categoryService.save(categoryRequest);
        return "redirect:/categories/category-list";
    }

    @GetMapping("/category-list")
    public String showCategories(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "category-list";
    }

    @GetMapping("/{id}/edit")
    public String showEditCategoryForm(@PathVariable("id") Long id, Model model) {
        Optional<Category> category = categoryService.findById(id);
        if (category.isPresent()) {
            CategoryRequest categoryRequest = new CategoryRequest();
            categoryRequest.setId(category.get().getId());
            categoryRequest.setName(category.get().getName());
            categoryRequest.setDescription(category.get().getDescription());
            model.addAttribute("category", categoryRequest);
            return "edit-category";
        }
        return "redirect:/categories/category-list";
    }

    @PutMapping("/{id}/update")
    public String updateCategory(@PathVariable("id") Long id,
                                 @ModelAttribute CategoryRequest categoryRequest) {
        categoryRequest.setId(id);
        categoryService.update(categoryRequest, id);
        return "redirect:/categories/category-list";
    }

    @GetMapping("/{id}/delete")
    public String deleteCategory(@PathVariable("id") Long id) {
        categoryService.deleteById(id);
        return "redirect:/categories/category-list";
    }
}

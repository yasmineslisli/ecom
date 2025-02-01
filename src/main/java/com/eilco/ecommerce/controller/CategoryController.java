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

    // Show form for adding a new category
    @GetMapping("/add")
    public String showAddCategoryForm(Model model) {
        model.addAttribute("category", new CategoryRequest());
        return "add-category";  // Separate view for adding categories
    }

    // Handle adding a category
    @PostMapping("/add")
    public String addCategory(@ModelAttribute("category") CategoryRequest categoryRequest) {
        categoryService.save(categoryRequest);
        return "redirect:/categories/category-list";
    }

    // Show category list
    @GetMapping("/category-list")
    public String showCategories(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "category-list";
    }

    // Show form for editing an existing category
    @GetMapping("/{id}/edit")
    public String showEditCategoryForm(@PathVariable("id") Long id, Model model) {
        Optional<Category> category = categoryService.findById(id);
        if (category.isPresent()) {
            CategoryRequest categoryRequest = new CategoryRequest();
            categoryRequest.setId(category.get().getId());
            categoryRequest.setName(category.get().getName());
            categoryRequest.setDescription(category.get().getDescription());
            model.addAttribute("category", categoryRequest);
            return "edit-category";  // Separate view for editing categories
        }
        return "redirect:/categories/category-list";
    }

    // Handle updating a category using PUT
    @PutMapping("/{id}/update")
    public String updateCategory(@PathVariable("id") Long id,
                                 @ModelAttribute CategoryRequest categoryRequest) {
        categoryRequest.setId(id);
        categoryService.update(categoryRequest, id);
        return "redirect:/categories/category-list";
    }

    // Handle deleting a category
    @GetMapping("/{id}/delete")
    public String deleteCategory(@PathVariable("id") Long id) {
        categoryService.deleteById(id);
        return "redirect:/categories/category-list";
    }
}

package com.eilco.ecommerce.controller;

import com.eilco.ecommerce.dto.CategoryRequest;
import com.eilco.ecommerce.dto.CategoryResponse;
import com.eilco.ecommerce.model.entities.Category;
import com.eilco.ecommerce.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse addcategory(@RequestBody CategoryRequest categoryRequest)
    {
        return CategoryResponse.builder().category(categoryService.save(categoryRequest)).build();
    }

    @GetMapping("/{id}")
    public Optional<Category> getCategory(@PathVariable("id") Long id){
        return categoryService.findById(id);
    }
    @GetMapping()
    public List<Category> list(){
        return categoryService.findAll();
    }

    @PutMapping("/{id}")
    public CategoryResponse updateCategory(@RequestBody CategoryRequest CategoryRequest, @PathVariable("id") Long id){
        return CategoryResponse.builder().category(categoryService.update(CategoryRequest,id)).build();
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable("id") Long id){
        categoryService.deleteById(id);
    }



}

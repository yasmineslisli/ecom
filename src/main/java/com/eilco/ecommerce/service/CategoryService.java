package com.eilco.ecommerce.service;

import com.eilco.ecommerce.dto.CategoryRequest;
import com.eilco.ecommerce.model.entities.Category;
import com.eilco.ecommerce.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CategoryService {

    @Autowired
    private final CategoryRepository repository;


    public Category save(CategoryRequest categoryRequest){
        return repository.save(convertCategoryRequestToCategory(categoryRequest, null));
    }

    public Category update(CategoryRequest categoryRequest, Long id){
        return repository.save(convertCategoryRequestToCategory(categoryRequest, id));
    }

    public List<Category> findAll(){
        return (List<Category>) repository.findAll();
    }

    public void deleteById(Long id){
        repository.deleteById(id);
    }

    public Optional<Category> findById(Long id){
        return repository.findById(id);
    }
    public List<Category> getAllCategories() {
        return repository.findAll();
    }

    private Category convertCategoryRequestToCategory(CategoryRequest categoryRequest, Long id){
        return Category.builder()
                .id(id)
                .name(categoryRequest.getName())
                .description(categoryRequest.getDescription())
                .build();
    }
}

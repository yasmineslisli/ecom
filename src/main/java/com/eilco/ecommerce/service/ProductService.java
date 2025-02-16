package com.eilco.ecommerce.service;

import com.eilco.ecommerce.dto.ProductRequest;
import com.eilco.ecommerce.dto.ProductResponse;
import com.eilco.ecommerce.model.entities.Category;
import com.eilco.ecommerce.model.entities.Product;
import com.eilco.ecommerce.repository.CategoryRepository;
import com.eilco.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductResponse save(ProductRequest productRequest) {
        Product product = convertProductRequestToProduct(productRequest, null);
        return convertProductToResponse(productRepository.save(product));
    }

    public ProductResponse update(ProductRequest productRequest, Long id) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        // Update fields while preserving the existing entity references
        existingProduct.setName(productRequest.getName());
        existingProduct.setDescription(productRequest.getDescription());
        existingProduct.setPrice(productRequest.getPrice());
        existingProduct.setQuantity(productRequest.getQuantity());
        existingProduct.setActive(productRequest.isActive());
        existingProduct.setImageUrl(productRequest.getImageUrl());

        // Update the category reference properly
        if (productRequest.getCategoryId() != null) {
            Category category = categoryRepository.findById(productRequest.getCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("Category not found"));
            existingProduct.setCategory(category);
        }

        productRepository.save(existingProduct);
        return convertProductToResponse(existingProduct);
    }


    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    private Product convertProductRequestToProduct(ProductRequest productRequest, Long id) {
        Category category = categoryRepository.findById(productRequest.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found with ID: " + productRequest.getCategoryId()));

        return Product.builder()
                .id(id)
                .name(productRequest.getName())
                .price(productRequest.getPrice())
                .imageUrl(productRequest.getImageUrl())
                .quantity(productRequest.getQuantity())
                .description(productRequest.getDescription())
                .active(productRequest.isActive())
                .category(category)
                .build();
    }

    public ProductResponse convertProductToResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .imageUrl(product.getImageUrl())
                .quantity(product.getQuantity())
                .active(product.isActive())
                .categoryName(product.getCategory() != null ? product.getCategory().getName() : null)
                .categoryId(product.getCategory().getId())
                .build();
    }
}
